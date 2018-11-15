package com.udacity.baking.net.TO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.baking.models.Ingredient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class IngredientTO implements Parcelable {

    @SerializedName("quantity")
    @Expose
    private Double quantity;

    @SerializedName("measure")
    @Expose
    private String measure;

    @SerializedName("ingredient")
    @Expose
    private String ingredient;


    public final static Parcelable.Creator<IngredientTO> CREATOR = new Creator<IngredientTO>() {

        @SuppressWarnings( {"unchecked"} )
        public IngredientTO createFromParcel(Parcel in) {
            return new IngredientTO(in);
        }

        public IngredientTO[] newArray(int size) {
            return (new IngredientTO[size]);
        }

    };

    protected IngredientTO(Parcel in) {
        this.quantity   = ((Double) in.readValue((Double.class.getClassLoader())));
        this.measure    = ((String) in.readValue((String.class.getClassLoader())));
        this.ingredient = ((String) in.readValue((String.class.getClassLoader())));
    }

    public IngredientTO() {
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("quantity", this.quantity)
                .append("measure", this.measure)
                .append("ingredient", this.ingredient)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.measure)
                .append(this.ingredient)
                .append(this.quantity)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IngredientTO) == false) {
            return false;
        }
        IngredientTO rhs = ((IngredientTO) other);
        return new EqualsBuilder()
                .append(this.measure, rhs.measure)
                .append(this.ingredient, rhs.ingredient)
                .append(this.quantity, rhs.quantity)
                .isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.quantity);
        dest.writeValue(this.measure);
        dest.writeValue(this.ingredient);
    }

    public int describeContents() {
        return 0;
    }

    public static Ingredient toModel(IngredientTO to) {
        Ingredient result = new Ingredient();
        result.setQuantity(to.quantity);
        result.setMeasure(to.measure);
        result.setIngredient(to.ingredient);
        return result;
    }

    public static List<Ingredient> toListModel(List<IngredientTO> tos) {
        List<Ingredient> result = new ArrayList<>();
        for (IngredientTO to : tos) {
            result.add(toModel(to));
        }
        return result;
    }
}
