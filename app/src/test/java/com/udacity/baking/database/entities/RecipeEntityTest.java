package com.udacity.baking.database.entities;

import android.os.Parcel;

import com.udacity.baking.models.Ingredient;
import com.udacity.baking.models.Recipe;
import com.udacity.baking.models.Step;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RecipeEntityTest {

    private RecipeEntity given;
    private RecipeEntity expected;

    private Parcel parcel;
    private Class recipeEntityClass = RecipeEntity.class;
    private Constructor constructorParcel;

    @Before
    public void setUp() throws Exception {
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

        expected = new RecipeEntity();
        expected.getRecipe().setId(1);
        expected.getRecipe().setName("1");
        expected.getRecipe().setImage("1");
        expected.setIngredients(new ArrayList<>());
        expected.getIngredients().add(new IngredientEntity());
        expected.getIngredients().get(0).setId(1);
        expected.getIngredients().get(0).setRecipeId(1);
        expected.getIngredients().get(0).setIngredient("1");
        expected.getIngredients().get(0).setMeasure("1");
        expected.getIngredients().get(0).setQuantity(1.0);
        expected.setSteps(new ArrayList<>());
        expected.getSteps().add(new StepEntity());
        expected.getSteps().get(0).setStepNumber(1);
        expected.getSteps().get(0).setRecipeId(1);
        expected.getSteps().get(0).setShortDescription("1");
        expected.getSteps().get(0).setDescription("1");
        expected.getSteps().get(0).setThumbnailURL("/");
        expected.getSteps().get(0).setVideoURL("/");
        expected.getRecipe().setServings(0);

        parcel = Parcel.obtain();
    }

    @Test
    public void equalsValidator_ShouldReturnTrue() {
        final String TAG_T = "equalsValidator_ShouldReturnTrue ";
        assertEquals(TAG_T, this.expected,(given));
        assertEquals(TAG_T , this.expected,(expected));
        given = expected;
        assertEquals(TAG_T, this.expected,(given));
    }

    @Test
    public void toStringValidator_ShouldReturnTrue() {
        final String TAG_T = "toStringValidator_ShouldReturnTrue " ;
        assertEquals(TAG_T, this.expected.toString(),(expected.toString()));
    }

    @Test
    public void equalsNullValidator_ShouldReturnFalse() {
        final String TAG_T = "equalsNullValidator_ShouldReturnFalse " ;
        given = null;
        assertNotEquals(TAG_T, this.expected, given);
    }

    @Test
    public void hashCodeValidator_ShouldReturnTrue() {
        final String TAG_T = "hashCodeValidator_ShouldReturnTrue ";
        assertEquals(TAG_T, this.expected.hashCode(), this.given.hashCode());
    }

    @Test
    public void gettersValidator_ShouldReturnTrue() {
        final String TAG_T = "gettersValidator_ShouldReturnTrue " ;
        assertEquals(TAG_T, this.expected.getRecipe(), (this.given.getRecipe()));
        assertEquals(TAG_T, this.expected.getIngredients(), (this.given.getIngredients()));
        assertEquals(TAG_T, this.expected.getSteps(), (this.given.getSteps()));
    }

    @Test
    public void parcelValidator_ShouldReturnTrue() {
        final String TAG_T = "parcelValidator_ShouldReturnTrue ";
        try {
            constructorParcel = recipeEntityClass.getConstructor(Parcel.class);
            expected.writeToParcel(parcel, 0);
            constructorParcel.setAccessible(true);
            given = (RecipeEntity) constructorParcel.newInstance(parcel);
            assertEquals(TAG_T, expected, (given));

        } catch (Exception ex) {
            System.out.println(TAG_T+ ex.getMessage());
        }

    }

    @Test
    public void parcelDescribeContValidator_ShouldReturnTrue() {
        final String TAG_T = "gettersValidator_ShouldReturnTrue ";
        assertEquals(TAG_T, this.expected.describeContents(), this.given.describeContents());
    }

    @Test
    public void toModelValidators_ShouldReturnTrue() {
        final String TAG_T = "toModelValidators_ShouldReturnTrue " ;

        Recipe recipeExpected = new Recipe();
        recipeExpected.setId(expected.getRecipe().getId());
        recipeExpected.setName(expected.getRecipe().getName());
        recipeExpected.setIngredients(new ArrayList<Ingredient>());
        recipeExpected.getIngredients().add(new Ingredient());
        recipeExpected.getIngredients().get(0).setIngredient("1");
        recipeExpected.getIngredients().get(0).setMeasure("1");
        recipeExpected.getIngredients().get(0).setQuantity(1.0);
        recipeExpected.setSteps(new ArrayList<Step>());
        recipeExpected.getSteps().add(new Step());
        recipeExpected.getSteps().get(0).setId(1);
        recipeExpected.getSteps().get(0).setShortDescription("1");
        recipeExpected.getSteps().get(0).setDescription("1");
        recipeExpected.getSteps().get(0).setThumbnailURL("/");
        recipeExpected.getSteps().get(0).setVideoURL("/");
        recipeExpected.setServings(expected.getRecipe().getServings());
        recipeExpected.setImage(expected.getRecipe().getImage());

        assertEquals(TAG_T, recipeExpected, (RecipeEntity.toModel(given)));

        List<Recipe> listModel = new ArrayList<>();
        listModel.add(recipeExpected);
        List<RecipeEntity> listEntities = new ArrayList<>();
        listEntities.add(expected);
        assertEquals(TAG_T , listModel, (RecipeEntity.toListModel(listEntities)));

    }

    @Test
    public void toEntityValidators_ShouldReturnTrue() {
        final String TAG_T = "toEntityValidators_ShouldReturnTrue " ;
        Recipe recipeExpected = new Recipe();
        recipeExpected.setId(1);
        recipeExpected.setName("1");
        recipeExpected.setIngredients(new ArrayList<Ingredient>());
        recipeExpected.getIngredients().add(new Ingredient());
        recipeExpected.getIngredients().get(0).setIngredient("1");
        recipeExpected.getIngredients().get(0).setMeasure("1");
        recipeExpected.getIngredients().get(0).setQuantity(1.0);
        recipeExpected.setSteps(new ArrayList<Step>());
        recipeExpected.getSteps().add(new Step());
        recipeExpected.getSteps().get(0).setId(1);
        recipeExpected.getSteps().get(0).setShortDescription("1");
        recipeExpected.getSteps().get(0).setDescription("1");
        recipeExpected.getSteps().get(0).setThumbnailURL("/");
        recipeExpected.getSteps().get(0).setVideoURL("/");
        recipeExpected.setServings(0);
        recipeExpected.setImage("1");
        expected.getIngredients().get(0).setId(null);
        assertEquals(TAG_T, expected, RecipeEntity.toEntity(recipeExpected));

        List<Recipe> listModel = new ArrayList<>();
        listModel.add(recipeExpected);
        List<RecipeEntity> listEntities = new ArrayList<>();
        listEntities.add(expected);
        assertEquals(TAG_T, listEntities, RecipeEntity.toListEntity(listModel));

    }
}