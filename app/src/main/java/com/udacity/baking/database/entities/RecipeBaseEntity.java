package com.udacity.baking.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
@Entity(tableName = "recipe"
        , indices = { @Index(name = "recipeId_index", value = { "id" }, unique = true) }
        )
public class RecipeBaseEntity {

    @PrimaryKey
    @NonNull
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "servings")
    private Integer servings;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "createdDate")
    private Long createdDate = System.currentTimeMillis();

    public final static Parcelable.Creator<RecipeBaseEntity> CREATOR = new Parcelable.Creator<RecipeBaseEntity>() {

        @SuppressWarnings( {"unchecked"} )
        public RecipeBaseEntity createFromParcel(Parcel in) {
            return new RecipeBaseEntity(in);
        }

        public RecipeBaseEntity[] newArray(int size) {
            return (new RecipeBaseEntity[size]);
        }

    };

    protected RecipeBaseEntity(Parcel in) {
        this.id       = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name     = ((String) in.readValue((String.class.getClassLoader())));
        this.servings = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.image    = ((String) in.readValue((String.class.getClassLoader())));
    }

    public RecipeBaseEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
                .append("name", this.name)
                .append("servings", this.servings)
                .append("image", this.image)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.servings)
                .append(this.name)
                .append(this.image)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RecipeBaseEntity) == false) {
            return false;
        }
        RecipeBaseEntity rhs = ((RecipeBaseEntity) other);
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.servings, rhs.servings)
                .append(this.name, rhs.name)
                .append(this.image, rhs.image)
                .isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.name);
        dest.writeValue(this.servings);
        dest.writeValue(this.image);
    }

    public int describeContents() {
        return 0;
    }



}
