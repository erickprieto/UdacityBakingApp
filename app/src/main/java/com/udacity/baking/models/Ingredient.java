package com.udacity.baking.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Ingredient implements Parcelable{


    private Double quantity;

    private String measure;

    private String ingredient;


    public final static Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @SuppressWarnings( {"unchecked"} )
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return (new Ingredient[size]);
        }

    };

    protected Ingredient(Parcel in) {
        this.quantity   = ((Double) in.readValue((Integer.class.getClassLoader())));
        this.measure    = ((String) in.readValue((String.class.getClassLoader())));
        this.ingredient = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Ingredient() {
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
        if ((other instanceof Ingredient) == false) {
            return false;
        }
        Ingredient rhs = ((Ingredient) other);
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

}
