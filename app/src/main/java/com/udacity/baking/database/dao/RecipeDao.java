package com.udacity.baking.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.view.View;

import com.udacity.baking.database.entities.IngredientEntity;
import com.udacity.baking.database.entities.RecipeBaseEntity;
import com.udacity.baking.database.entities.RecipeEntity;
import com.udacity.baking.database.entities.StepEntity;


import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
@Dao
public abstract class RecipeDao {

    @Query("SELECT * FROM recipe")
    public abstract LiveData<List<RecipeEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertIngredients(List<IngredientEntity> ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertSteps(List<StepEntity> steps);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRecipes(RecipeBaseEntity base);

    @Query("DELETE FROM ingredient")
    public abstract void deleteAllIngredients();

    @Query("DELETE FROM step")
    public abstract void deleteAllSteps();

    @Transaction
    public void insertAll(List<RecipeEntity> recipes) {
        deleteAllIngredients();
        deleteAllSteps();
        for (RecipeEntity recipe : recipes) {
            insertRecipes(recipe.getRecipe());
            insertIngredients(recipe.getIngredients());
            insertSteps(recipe.getSteps());
        }

    }
}
