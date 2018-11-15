package com.udacity.baking.net;

import android.os.Parcel;

import com.udacity.baking.models.Ingredient;
import com.udacity.baking.net.TO.IngredientTO;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IngredientTOTest {

    private IngredientTO given;
    private IngredientTO expected;

    private Parcel parcel;
    private Class ingredientTOClass = IngredientTO.class;
    private Constructor constructorParcel;

    @Before
    public void setUp() throws Exception {
      given = new IngredientTO();
      given.setQuantity(1.0);
      given.setMeasure("1");
      given.setIngredient("1");

      expected = new IngredientTO();
      expected.setQuantity(1.0);
      expected.setMeasure("1");
      expected.setIngredient("1");
      parcel = Parcel.obtain();
    }

    @Test
    public void equalsValidator_ShouldReturnTrue() {
        final String TAG_T = "equalsValidator_ShouldReturnTrue ";
        assertEquals(TAG_T , this.expected, given);
        assertEquals(TAG_T , this.expected, expected);
        given = expected;
        assertEquals(TAG_T, this.expected, (given));
    }

    @Test
    public void toStringValidator_ShouldReturnTrue() {
        final String TAG_T = "toStringValidator_ShouldReturnTrue ";
        assertEquals(TAG_T, this.expected.toString(), expected.toString());
    }

    @Test
    public void equalsNullValidator_ShouldReturnFalse() {
        final String TAG_T = "equalsNullValidator_ShouldReturnFalse ";
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
        final String TAG_T = "gettersValidator_ShouldReturnTrue ";
        assertEquals(TAG_T, this.expected.getQuantity(), this.given.getQuantity());
        assertEquals(TAG_T, this.expected.getIngredient(), this.given.getIngredient());
        assertEquals(TAG_T, this.expected.getMeasure(), this.given.getMeasure());
    }

    @Test
    public void parcelValidator_ShouldReturnTrue() {
        final String TAG_T = "parcelValidator_ShouldReturnTrue ";
        try {
            expected = null;
            constructorParcel = ingredientTOClass.getConstructor(Parcel.class);
            expected.writeToParcel(parcel, 0);
            constructorParcel.setAccessible(true);
            given = (IngredientTO) constructorParcel.newInstance(parcel);
            assertEquals(TAG_T, expected, given);

        } catch (Exception ex) {
            System.out.println(TAG_T + ex.getMessage());
        }

    }

    @Test
    public void parcelDescribeContValidator_ShouldReturnTrue() {
        final String TAG_T = "parcelDescribeContValidator_ShouldReturnTrue " ;
        assertEquals(TAG_T, this.expected.describeContents(), this.given.describeContents());
    }

    @Test
    public void toModelValidators_ShouldReturnTrue() {
        final String TAG_T = "toModelValidators_ShouldReturnTrue " ;
        Ingredient ingredientExpected = new Ingredient();
        ingredientExpected.setQuantity(expected.getQuantity());
        ingredientExpected.setMeasure(expected.getMeasure());
        ingredientExpected.setIngredient(expected.getIngredient());

        assertEquals(TAG_T, ingredientExpected, IngredientTO.toModel(given));


        List<Ingredient> listModel = new ArrayList<>();
        listModel.add(ingredientExpected);
        List<IngredientTO> listTO = new ArrayList<>();
        listTO.add(expected);
        assertEquals(TAG_T, listModel, (IngredientTO.toListModel(listTO)));
    }

}