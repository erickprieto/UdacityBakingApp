package com.udacity.baking.database.dao;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.baking.database.BakingDatabase;
import com.udacity.baking.database.entities.IngredientEntity;
import com.udacity.baking.database.entities.RecipeEntity;
import com.udacity.baking.database.entities.StepEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RecipeDaoTest {

    private BakingDatabase db;
    private RecipeEntity given;
    private List<RecipeEntity> listGiven;
    private LifecycleOwner lifeCycleOwner;

    @Before
    public void setUp() throws Exception {

        lifeCycleOwner = new LifecycleOwner() {
            @NonNull
            @Override
            public Lifecycle getLifecycle() {
                return new Lifecycle() {
                    @Override
                    public void addObserver(@NonNull LifecycleObserver observer) {

                    }

                    @Override
                    public void removeObserver(@NonNull LifecycleObserver observer) {

                    }

                    @NonNull
                    @Override
                    public State getCurrentState() {
                        return null;
                    }
                };
            }
        };

        db = Room
                .inMemoryDatabaseBuilder(getTargetContext(), BakingDatabase.class)
                .build();

        given = new RecipeEntity();
        given.getRecipe().setId(1);
        given.getRecipe().setName("1");
        given.getRecipe().setImage("1");
        given.setIngredients(new ArrayList<>());
        given.getIngredients().add(new IngredientEntity());
        given.getIngredients().get(0).setId(1);
        given.getIngredients().get(0).setRecipeId(1);
        given.getIngredients().get(0).setIngredient("1");
        given.getIngredients().get(0).setMeasure("1");
        given.getIngredients().get(0).setQuantity(1.0);
        given.setSteps(new ArrayList<>());
        given.getSteps().add(new StepEntity());
        given.getSteps().get(0).setStepNumber(1);
        given.getSteps().get(0).setRecipeId(1);
        given.getSteps().get(0).setShortDescription("1");
        given.getSteps().get(0).setDescription("1");
        given.getSteps().get(0).setThumbnailURL("/");
        given.getSteps().get(0).setVideoURL("/");
        given.getRecipe().setServings(0);

        listGiven = new ArrayList<>();
        listGiven.add(given);

    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void writeAndRead() {
        final String TAG_M = "writeAndRead ";
        LiveData<List<RecipeEntity>> result = db.recipeDao().getAll();
        db.recipeDao().insertAll(listGiven);

        result.observe(this.lifeCycleOwner, new Observer<List<RecipeEntity>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntity> recipeEntities) {
                assertEquals(TAG_M, listGiven, recipeEntities);
            }
        });


    }
}