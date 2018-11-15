package com.udacity.baking.net;

import android.os.Parcel;

import com.udacity.baking.models.Step;
import com.udacity.baking.net.TO.StepTO;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StepTOTest {

    private StepTO given;
    private StepTO expected;

    private Parcel parcel;
    private Class stepTOClass = StepTO.class;
    private Constructor constructorParcel;

    @Before
    public void setUp() throws Exception {
        given = new StepTO();
        given.setId(1);
        given.setDescription("1");
        given.setShortDescription("1");
        given.setThumbnailURL("1");
        given.setVideoURL("/");
        expected = new StepTO();
        expected.setId(1);
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
        assertEquals(TAG_T, this.expected.getDescription(), this.given.getDescription());
        assertEquals(TAG_T, this.expected.getShortDescription(), this.given.getShortDescription());
        assertEquals(TAG_T, this.expected.getThumbnailURL(), this.given.getThumbnailURL());
        assertEquals(TAG_T, this.expected.getVideoURL(), this.given.getVideoURL());
    }

    @Test
    public void parcelValidator_ShouldReturnTrue() {
        final String TAG_T = "parcelValidator_ShouldReturnTrue ";
        try {
            constructorParcel = stepTOClass.getConstructor(Parcel.class);
            expected.writeToParcel(parcel, 0);
            constructorParcel.setAccessible(true);
            given = (StepTO) constructorParcel.newInstance(parcel);
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
        stepExpected.setId(1);
        stepExpected.setDescription("1");
        stepExpected.setShortDescription("1");
        stepExpected.setThumbnailURL("1");
        stepExpected.setVideoURL("/");

        assertEquals(TAG_T, stepExpected, (StepTO.toModel(given)));

        List<Step> listModel = new ArrayList<>();
        listModel.add(stepExpected);
        List<StepTO> listTO = new ArrayList<>();
        listTO.add(expected);
        assertEquals(TAG_T, listModel, (StepTO.toListModel(listTO)));

    }

    @Test
    public void toModelBugFixValidators_ShouldReturnTrue() {
        final String TAG_T = "toModelBugFixValidators_ShouldReturnTrue ";
        given.setThumbnailURL("/");
        given.setVideoURL(StringUtils.EMPTY);


        Step stepExpected = new Step();
        stepExpected.setId(1);
        stepExpected.setDescription("1");
        stepExpected.setShortDescription("1");
        stepExpected.setThumbnailURL(StringUtils.EMPTY);
        stepExpected.setVideoURL("/");

        assertEquals(TAG_T, stepExpected, (StepTO.toModel(given)));

        given.setThumbnailURL(StringUtils.EMPTY);
        given.setVideoURL("/");

        stepExpected.setThumbnailURL(StringUtils.EMPTY);
        stepExpected.setVideoURL("/");

        assertEquals(TAG_T, stepExpected, (StepTO.toModel(given)));
    }
}