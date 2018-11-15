package com.udacity.baking.services;

import android.arch.lifecycle.LifecycleService;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.udacity.baking.BakingApplication;
import com.udacity.baking.database.BakingDatabase;
import com.udacity.baking.database.entities.RecipeEntity;
import com.udacity.baking.events.ListRecipesFetchedEvent;
import com.udacity.baking.models.Recipe;
import com.udacity.baking.net.TO.RecipeTO;
import com.udacity.baking.net.contracts.RecipeContract;
import com.udacity.baking.utils.AppExecutors;
import com.udacity.baking.utils.ProxyHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakingService extends LifecycleService {

    /**
     * Tag that identify all messages sent to loggger by this class.
     */
    private static final String TAG = BakingService.class.getSimpleName();

    private List<Recipe> recipes;

    private BakingDatabase db;

    private final IBinder binder = new BakingServiceBinder();


    @Override
    public void onCreate() {
        super.onCreate();
        BakingApplication.getEventBus().register(this);
        db = BakingDatabase.getDatabase(this);

        db.recipeDao().getAll().observe(this, new Observer<List<RecipeEntity>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntity> recipeEntities) {
                recipes = RecipeEntity.toListModel(recipeEntities);
                BakingApplication.getEventBus().post(new ListRecipesFetchedEvent(recipes));
                Log.v(TAG, recipes.toString());
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String TAG_M = "onStartCommand ";
        Log.v(TAG, TAG_M);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        BakingApplication.getEventBus().unregister(this);
        super.onDestroy();
    }

    public List<Recipe> getRecipes() {
        if (recipes == null || recipes.size() == 0) {
            fetchRecipes();
            return null;
        } else {
           return recipes;
        }
    }

    public void fetchRecipes() {
        final String TAG_M = "fetchRecipes";
        RecipeContract contract = ProxyHelper.getProxy(RecipeContract.class);
        contract.getRecipes().enqueue(new Callback<List<RecipeTO>>() {

            @Override
            public void onResponse(Call<List<RecipeTO>> call, Response<List<RecipeTO>> response) {
                try {
                    if(response.isSuccessful()) {
                        Log.v(TAG,TAG_M + "Successful");
                        List<RecipeTO> recipesTO = response.body();
                        recipes = RecipeTO.toListModel(recipesTO);
                        saveRecipes();
                    } else {
                    }
                }catch (Exception ex) {
                    Log.e(TAG,TAG_M + "failed", ex);
                }

            }

            @Override
            public void onFailure(Call<List<RecipeTO>> call, Throwable t) {
                Log.e(TAG,TAG_M + "failed", t);
            }
        });
    }

    private void saveRecipes(){
        List<RecipeEntity> entities = RecipeEntity.toListEntity(recipes);
        AppExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.recipeDao().insertAll(entities);
            }
        });

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return this.binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class BakingServiceBinder extends Binder {

       public BakingService getService() {
           return BakingService.this;
       }
    }

}
