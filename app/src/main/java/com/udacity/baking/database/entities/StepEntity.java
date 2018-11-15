package com.udacity.baking.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.udacity.baking.models.Step;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "step",foreignKeys =
@ForeignKey(entity=RecipeBaseEntity.class, parentColumns = "id", childColumns = "recipeId"))
public class StepEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "stepNumber")
    private Integer stepNumber;

    @ColumnInfo(name = "recipeId")
    private Integer recipeId;

    @ColumnInfo(name = "shortDescription")
    private String shortDescription;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "videoURL")
    private String videoURL;

    @ColumnInfo(name = "thumbnailURL")
    private String thumbnailURL;

    @ColumnInfo(name = "createdDate")
    private Long createdDate = System.currentTimeMillis();

    @Ignore
    public final static Parcelable.Creator<StepEntity> CREATOR = new Parcelable.Creator<StepEntity>() {

        @SuppressWarnings( {"unchecked"} )
        public StepEntity createFromParcel(Parcel in) {
            return new StepEntity(in);
        }

        public StepEntity[] newArray(int size) {
            return (new StepEntity[size]);
        }

    };

    @Ignore
    protected StepEntity(Parcel in) {
        this.id               = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stepNumber       = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.recipeId         = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.shortDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.description      = ((String) in.readValue((String.class.getClassLoader())));
        this.videoURL         = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnailURL     = ((String) in.readValue((String.class.getClassLoader())));
    }

    public StepEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Integer stepNumber) {
        this.stepNumber = stepNumber;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
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
                .append("stepNumber", this.stepNumber)
                .append("recipeId", this.recipeId)
                .append("shortDescription", this.shortDescription)
                .append("description", StringUtils.substring(this.description, 0, 20) + "...")
                .append("videoURL", this.videoURL)
                .append("thumbnailURL", this.thumbnailURL)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.stepNumber)
                .append(this.recipeId)
                .append(this.shortDescription)
                .append(this.description)
                .append(this.videoURL)
                .append(this.thumbnailURL)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof StepEntity) == false) {
            return false;
        }
        StepEntity rhs = ((StepEntity) other);
        return new EqualsBuilder().append(id, rhs.id)
                .append(this.stepNumber, rhs.stepNumber)
                .append(this.recipeId, rhs.recipeId)
                .append(this.shortDescription, rhs.shortDescription)
                .append(this.description, rhs.description)
                .append(this.videoURL, rhs.videoURL)
                .append(this.thumbnailURL, rhs.thumbnailURL)
                .isEquals();
    }

    @Ignore
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.stepNumber);
        dest.writeValue(this.recipeId);
        dest.writeValue(this.shortDescription);
        dest.writeValue(this.description);
        dest.writeValue(this.videoURL);
        dest.writeValue(this.thumbnailURL);
    }

    @Ignore
    public int describeContents() {
        return 0;
    }

    @Ignore
    public static StepEntity toEntity(Step model, Integer recipeId) {
        StepEntity entity = new StepEntity();
        entity.stepNumber = model.getId();
        entity.recipeId = recipeId;
        entity.shortDescription = model.getShortDescription();
        entity.description = model.getDescription();
        entity.videoURL = model.getVideoURL();
        entity.thumbnailURL = model.getThumbnailURL();

        return entity;
    }

    @Ignore
    public static Step toModel(StepEntity entity) {
        Step model = new Step();
        model.setId(entity.stepNumber);
        model.setShortDescription(entity.shortDescription);
        model.setDescription(entity.description);
        model.setVideoURL(entity.videoURL);
        model.setThumbnailURL(entity.thumbnailURL);
        return model;
    }

    @Ignore
    public static List<Step> toListModel(List<StepEntity> entities) {
        List<Step> result = new ArrayList<>();
        for (StepEntity entity : entities) {
            result.add(toModel(entity));
        }
        return result;
    }

    @Ignore
    public static List<StepEntity> toListEntity(List<Step> models, Integer recipeId) {
        List<StepEntity> result = new ArrayList<>();
        for (Step model : models) {
            result.add(toEntity(model, recipeId));
        }
        return result;
    }


}
