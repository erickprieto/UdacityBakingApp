package com.udacity.baking.events;

import com.udacity.baking.models.Recipe;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class onRecipeDetailsRequestEvent {

    private final Recipe recipe;

    public onRecipeDetailsRequestEvent(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
