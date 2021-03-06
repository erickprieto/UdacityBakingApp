package com.udacity.baking.models;

import android.os.Parcel;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RecipeTest {

    private Recipe given;
    private Recipe expected;

    private Parcel parcel;
    private Class recipeClass = Recipe.class;
    private Constructor constructorParcel;

    @Before
    public void setUp() throws Exception {
        given = new Recipe();
        given.setId(1);
        given.setName("1");
        given.setImage("1");
        given.setIngredients(new ArrayList<>());
        given.setSteps(new ArrayList<>());
        given.setServings(0);
        expected = new Recipe();
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
            constructorParcel = recipeClass.getConstructor(Parcel.class);
            expected.writeToParcel(parcel, 0);
            constructorParcel.setAccessible(true);
            given = (Recipe) constructorParcel.newInstance(parcel);
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

}