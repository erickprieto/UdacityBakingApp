package com.udacity.baking.net;

import android.os.Parcel;

import com.udacity.baking.models.Ingredient;
import com.udacity.baking.models.Recipe;
import com.udacity.baking.models.Step;
import com.udacity.baking.net.TO.RecipeTO;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RecipeTOTest {

    private RecipeTO given;
    private RecipeTO expected;

    private Parcel parcel;
    private Class recipeTOClass = RecipeTO.class;
    private Constructor constructorParcel;

    @Before
    public void setUp() throws Exception {
        given = new RecipeTO();
        given.setId(1);
        given.setName("1");
        given.setImage("1");
        given.setIngredients(new ArrayList<>());
        given.setSteps(new ArrayList<>());
        given.setServings(0);
        expected = new RecipeTO();
        expected.setId(1);
        expected.setName("1");
        expected.setImage("1");
        expected.setIngredients(new ArrayList<>());
        expected.setSteps(new ArrayList<>());
        expected.setServings(0);
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
        assertEquals(TAG_T, this.expected.getId(), (this.given.getId()));
        assertEquals(TAG_T, this.expected.getName(), (this.given.getName()));
        assertEquals(TAG_T, this.expected.getImage(), (this.given.getImage()));
        assertEquals(TAG_T, this.expected.getIngredients(), (this.given.getIngredients()));
        assertEquals(TAG_T, this.expected.getSteps(), (this.given.getSteps()));
        assertEquals(TAG_T, this.expected.getServings(), (this.given.getServings()));
    }

    @Test
    public void parcelValidator_ShouldReturnTrue() {
        final String TAG_T = "parcelValidator_ShouldReturnTrue ";
        try {
            constructorParcel = recipeTOClass.getConstructor(Parcel.class);
            expected.writeToParcel(parcel, 0);
            constructorParcel.setAccessible(true);
            given = (RecipeTO) constructorParcel.newInstance(parcel);
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
        recipeExpected.setId(1);
        recipeExpected.setName("1");
        recipeExpected.setIngredients(new ArrayList<Ingredient>());
        recipeExpected.setSteps(new ArrayList<Step>());
        recipeExpected.setServings(0);
        recipeExpected.setImage("1");

        assertEquals(TAG_T, recipeExpected, (RecipeTO.toModel(given)));

        List<Recipe> listModel = new ArrayList<>();
        listModel.add(recipeExpected);
        List<RecipeTO> listTO = new ArrayList<>();
        listTO.add(expected);
        assertEquals(TAG_T , listModel, (RecipeTO.toListModel(listTO)));

    }
}