package com.udacity.baking.database.entities;

import android.os.Parcel;

import com.udacity.baking.models.Step;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StepEntityTest {

    private StepEntity given;
    private StepEntity expected;

    private Parcel parcel;
    private Class stepEntityClass = StepEntity.class;
    private Constructor constructorParcel;

    @Before
    public void setUp() throws Exception {
        given = new StepEntity();
        given.setStepNumber(1);
        given.setRecipeId(1);
        given.setDescription("1");
        given.setShortDescription("1");
        given.setThumbnailURL("1");
        given.setVideoURL("/");
        expected = new StepEntity();
        expected.setStepNumber(1);
        expected.setRecipeId(1);
        expected.setDescription("1");
        expected.setShortDescription("1");
        expected.setThumbnailURL("1");
        expected.setVideoURL("/");
        parcel = Parcel.obtain();
    }

    @Test
    public void equalsValidator_ShouldReturnTrue() {
        final String TAG_T = "equalsValidator_ShouldReturnTrue ";
        assertEquals(TAG_T, this.expected, given);
        assertEquals(TAG_T, this.expected, expected);
        given = expected;
        assertEquals(TAG_T, this.expected, given);
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
        assertEquals(TAG_T, this.expected.getId() , this.given.getId());
        assertEquals(TAG_T, this.expected.getStepNumber() , this.given.getStepNumber());
        assertEquals(TAG_T, this.expected.getRecipeId() , this.given.getRecipeId());
        assertEquals(TAG_T, this.expected.getDescription(), this.given.getDescription());
        assertEquals(TAG_T, this.expected.getShortDescription(), this.given.getShortDescription());
        assertEquals(TAG_T, this.expected.getThumbnailURL(), this.given.getThumbnailURL());
        assertEquals(TAG_T, this.expected.getVideoURL(), this.given.getVideoURL());
    }

    @Test
    public void parcelValidator_ShouldReturnTrue() {
        final String TAG_T = "parcelValidator_ShouldReturnTrue ";
        try {
            constructorParcel = stepEntityClass.getConstructor(Parcel.class);
            expected.writeToParcel(parcel, 0);
            constructorParcel.setAccessible(true);
            given = (StepEntity) constructorParcel.newInstance(parcel);
            assertEquals(TAG_T, expected, (given));

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
        final String TAG_T = "toModelValidators_ShouldReturnTrue ";
        Step stepExpected = new Step();
        stepExpected.setId(expected.getStepNumber());
        stepExpected.setDescription(expected.getDescription());
        stepExpected.setShortDescription(expected.getShortDescription());
        stepExpected.setThumbnailURL(expected.getThumbnailURL());
        stepExpected.setVideoURL(expected.getVideoURL());

        assertEquals(TAG_T, stepExpected, StepEntity.toModel(expected));

        List<Step> listModel = new ArrayList<>();
        listModel.add(stepExpected);
        List<StepEntity> listTO = new ArrayList<>();
        listTO.add(expected);
        assertEquals(TAG_T, listModel, StepEntity.toListModel(listTO));

    }

    @Test
    public void toEntityValidators_ShouldReturnTrue() {
        final String TAG_T = "toEntityValidators_ShouldReturnTrue " ;
        Step stepExpected = new Step();
        stepExpected.setId(expected.getStepNumber());
        stepExpected.setDescription(expected.getDescription());
        stepExpected.setShortDescription(expected.getShortDescription());
        stepExpected.setThumbnailURL(expected.getThumbnailURL());
        stepExpected.setVideoURL(expected.getVideoURL());
        assertEquals(TAG_T, expected, StepEntity.toEntity(stepExpected, 1));

        List<Step> listModel = new ArrayList<>();
        listModel.add(stepExpected);
        List<StepEntity> listEntities = new ArrayList<>();
        listEntities.add(expected);
        assertEquals(TAG_T, listEntities, StepEntity.toListEntity(listModel, 1));

    }
}