package com.udacity.baking.database.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;
import android.os.Parcel;

import com.udacity.baking.models.Ingredient;
import com.udacity.baking.models.Recipe;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class RecipeEntity  {

    @Embedded
    private RecipeBaseEntity recipe = new RecipeBaseEntity();

    @Relation(parentColumn = "id", entityColumn = "recipeId", entity = IngredientEntity.class)
    private List<IngredientEntity> ingredients = new ArrayList<IngredientEntity>();

    @Relation(parentColumn = "id", entityColumn = "recipeId", entity = StepEntity.class)
    private List<StepEntity> steps = new ArrayList<StepEntity>();


    public RecipeEntity() {
    }

    public RecipeBaseEntity getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeBaseEntity recipe) {
        this.recipe = recipe;
    }

    public List<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<StepEntity> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("recipe", this.recipe)
                .append("ingredients", this.ingredients)
                .append("steps", this.steps)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.recipe)
                .append(this.ingredients)
                .append(this.steps)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RecipeEntity) == false) {
            return false;
        }
        RecipeEntity rhs = ((RecipeEntity) other);
        return new EqualsBuilder()
                .append(this.recipe, rhs.recipe)
                .append(this.ingredients, rhs.ingredients)
                .append(this.steps, rhs.steps)
                .isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.recipe);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
    }

    public int describeContents() {
        return 0;
    }

    @Ignore
    public static RecipeEntity toEntity(Recipe model) {
        RecipeEntity entity = new RecipeEntity();
        entity.recipe.setId(model.getId());
        entity.recipe.setName(model.getName());
        entity.ingredients = IngredientEntity.toListEntity(model.getIngredients(), model.getId());
        entity.steps = StepEntity.toListEntity(model.getSteps(), model.getId());
        entity.recipe.setImage(model.getImage());
        entity.recipe.setServings(model.getServings());
        return entity;
    }

    @Ignore
    public static Recipe toModel(RecipeEntity entity) {
        Recipe model = new Recipe();
        model.setId(entity.recipe.getId());
        model.setName(entity.recipe.getName());
        model.setIngredients(IngredientEntity.toListModel(entity.ingredients));
        model.setSteps(StepEntity.toListModel(entity.steps));
        model.setImage(entity.recipe.getImage());
        model.setServings(entity.recipe.getServings());
        return model;
    }

    @Ignore
    public static List<Recipe> toListModel(List<RecipeEntity> entities) {
        List<Recipe> result = new ArrayList<>();
        for (RecipeEntity entity : entities) {
            result.add(toModel(entity));
        }
        return result;
    }

    @Ignore
    public static List<RecipeEntity> toListEntity(List<Recipe> models) {
        List<RecipeEntity> result = new ArrayList<>();
        for (Recipe model : models) {
            result.add(toEntity(model));
        }
        return result;
    }


}
