package com.udacity.baking.events;

import com.udacity.baking.models.Recipe;

import java.util.List;

public class ListRecipesFetchedEvent {
    private List<Recipe> recipes;

    public ListRecipesFetchedEvent(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
