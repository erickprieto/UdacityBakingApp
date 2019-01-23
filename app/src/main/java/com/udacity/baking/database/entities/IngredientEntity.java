package com.udacity.baking.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.udacity.baking.models.Ingredient;

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
@Entity(tableName = "ingredient",foreignKeys =
@ForeignKey(entity=RecipeBaseEntity.class, parentColumns = "id", childColumns = "recipeId"))
public class IngredientEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    @ColumnInfo(name = "recipeId")
    private Integer recipeId;

    @ColumnInfo(name = "quantity")
    private Double quantity;

    @ColumnInfo(name = "measure")
    private String measure;

    @ColumnInfo(name = "ingredient")
    private String ingredient;

    @ColumnInfo(name = "createdDate")
    private Long createdDate = System.currentTimeMillis();

    @Ignore
    public final static Parcelable.Creator<IngredientEntity> CREATOR = new Parcelable.Creator<IngredientEntity>() {

        @SuppressWarnings( {"unchecked"} )
        public IngredientEntity createFromParcel(Parcel in) {
            return new IngredientEntity(in);
        }

        public IngredientEntity[] newArray(int size) {
            return (new IngredientEntity[size]);
        }

    };

    @Ignore
    protected IngredientEntity(Parcel in) {
        this.id         = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.recipeId   = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.quantity   = ((Double) in.readValue((Double.class.getClassLoader())));
        this.measure    = ((String) in.readValue((String.class.getClassLoader())));
        this.ingredient = ((String) in.readValue((String.class.getClassLoader())));
    }

    public IngredientEntity() {
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
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

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("recipeId", this.recipeId)
                .append("quantity", this.quantity)
                .append("measure", this.measure)
                .append("ingredient", this.ingredient)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.recipeId)
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
        if ((other instanceof IngredientEntity) == false) {
            return false;
        }
        IngredientEntity rhs = ((IngredientEntity) other);
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.recipeId, rhs.recipeId)
                .append(this.measure, rhs.measure)
                .append(this.ingredient, rhs.ingredient)
                .append(this.quantity, rhs.quantity)
                .isEquals();
    }

    @Ignore
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.recipeId);
        dest.writeValue(this.quantity);
        dest.writeValue(this.measure);
        dest.writeValue(this.ingredient);
    }

    @Ignore
    public int describeContents() {
        return 0;
    }

    @Ignore
    public static IngredientEntity toEntity(Ingredient model, Integer recipeId) {
        IngredientEntity entity = new IngredientEntity();
        entity.recipeId = recipeId;
        entity.quantity = model.getQuantity();
        entity.measure = model.getMeasure();
        entity.ingredient = model.getIngredient();
        return entity;
    }

    @Ignore
    public static Ingredient toModel(IngredientEntity entity) {
        Ingredient model = new Ingredient();
        model.setQuantity(entity.quantity);
        model.setMeasure(entity.measure);
        model.setIngredient(entity.ingredient);
        return model;
    }

    @Ignore
    public static List<Ingredient> toListModel(List<IngredientEntity> entities) {
        List<Ingredient> result = new ArrayList<>();
        for (IngredientEntity entity : entities) {
            result.add(toModel(entity));
        }
        return result;
    }

    @Ignore
    public static List<IngredientEntity> toListEntity(List<Ingredient> models, Integer recipeId) {
        List<IngredientEntity> result = new ArrayList<>();
        for (Ingredient model : models) {
            result.add(toEntity(model, recipeId));
        }
        return result;
    }


}