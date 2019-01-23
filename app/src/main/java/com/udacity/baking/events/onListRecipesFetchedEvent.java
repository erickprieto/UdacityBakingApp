package com.udacity.baking.events;

import com.udacity.baking.models.Recipe;

import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class onListRecipesFetchedEvent {

    private List<Recipe> recipes;

    public onListRecipesFetchedEvent(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
