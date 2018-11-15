package com.udacity.baking.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Step implements Parcelable {

    private Integer id;

    private String shortDescription;

    private String description;

    private String videoURL;

    private String thumbnailURL;

    public final static Parcelable.Creator<Step> CREATOR = new Creator<Step>() {


        @SuppressWarnings( {"unchecked"} )
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        public Step[] newArray(int size) {
            return (new Step[size]);
        }

    };

    protected Step(Parcel in) {
        this.id               = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.shortDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.description      = ((String) in.readValue((String.class.getClassLoader())));
        this.videoURL         = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnailURL     = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Step() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
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
        if ((other instanceof Step) == false) {
            return false;
        }
        Step rhs = ((Step) other);
        return new EqualsBuilder().append(id, rhs.id)
                .append(this.shortDescription, rhs.shortDescription)
                .append(this.description, rhs.description)
                .append(this.videoURL, rhs.videoURL)
                .append(this.thumbnailURL, rhs.thumbnailURL)
                .isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.shortDescription);
        dest.writeValue(this.description);
        dest.writeValue(this.videoURL);
        dest.writeValue(this.thumbnailURL);
    }

    public int describeContents() {
        return 0;
    }

}
