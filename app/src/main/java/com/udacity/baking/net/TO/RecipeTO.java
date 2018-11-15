package com.udacity.baking.net.TO;


import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.baking.models.Recipe;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class RecipeTO implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("ingredients")
    @Expose
    private List<IngredientTO> ingredients = new ArrayList<IngredientTO>();

    @SerializedName("steps")
    @Expose
    private List<StepTO> steps = new ArrayList<StepTO>();

    @SerializedName("servings")
    @Expose
    private Integer servings;

    @SerializedName("image")
    @Expose
    private String image;

    public final static Parcelable.Creator<RecipeTO> CREATOR = new Creator<RecipeTO>() {

        @SuppressWarnings( {"unchecked"} )
        public RecipeTO createFromParcel(Parcel in) {
            return new RecipeTO(in);
        }

        public RecipeTO[] newArray(int size) {
            return (new RecipeTO[size]);
        }

    };

    protected RecipeTO(Parcel in) {
        this.id       = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name     = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.ingredients, (IngredientTO.class.getClassLoader()));
        in.readList(this.steps, (StepTO.class.getClassLoader()));
        this.servings = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.image    = ((String) in.readValue((String.class.getClassLoader())));
    }

    public RecipeTO() {
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

    public List<IngredientTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientTO> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepTO> getSteps() {
        return steps;
    }

    public void setSteps(List<StepTO> steps) {
        this.steps = steps;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("name", this.name)
                .append("ingredients", this.ingredients)
                .append("steps", this.steps)
                .append("servings", this.servings)
                .append("image", this.image)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.name)
                .append(this.ingredients)
                .append(this.steps)
                .append(this.servings)
                .append(this.image)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RecipeTO) == false) {
            return false;
        }
        RecipeTO rhs = ((RecipeTO) other);
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.name, rhs.name)
                .append(this.ingredients, rhs.ingredients)
                .append(this.steps, rhs.steps)
                .append(this.servings, rhs.servings)
                .append(this.image, rhs.image)
                .isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeValue(this.servings);
        dest.writeValue(this.image);
    }

    public int describeContents() {
        return 0;
    }

    public static Recipe toModel(RecipeTO to) {
        Recipe result = new Recipe();
        result.setId(to.id);
        result.setName(to.name);
        result.setIngredients(IngredientTO.toListModel(to.ingredients));
        result.setSteps(StepTO.toListModel(to.steps));
        result.setServings(to.servings);
        result.setImage(to.image);
        return result;
    }

    public static List<Recipe> toListModel(List<RecipeTO> tos) {
        List<Recipe> result = new ArrayList<>();
        for (RecipeTO to : tos) {
            result.add(toModel(to));
        }
        return result;
    }
}
