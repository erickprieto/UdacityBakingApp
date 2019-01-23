package com.udacity.baking.events;

import com.udacity.baking.models.Step;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class onStepDetailsRequestEvent {

    private String recipeName;

    private Step step;

    public onStepDetailsRequestEvent(String recipeName, Step step) {
        this.recipeName = recipeName;
        this.step = step;
    }

    public Step getStep() {
        return this.step;
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
