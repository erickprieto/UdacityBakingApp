package com.udacity.baking.net.TO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.baking.models.Step;

import org.apache.commons.lang3.StringUtils;
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
public class StepTO implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("videoURL")
    @Expose
    private String videoURL;

    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;

    public final static Parcelable.Creator<StepTO> CREATOR = new Creator<StepTO>() {


        @SuppressWarnings( {"unchecked"} )
        public StepTO createFromParcel(Parcel in) {
            return new StepTO(in);
        }

        public StepTO[] newArray(int size) {
            return (new StepTO[size]);
        }

    };

    protected StepTO(Parcel in) {
        this.id               = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.shortDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.description      = ((String) in.readValue((String.class.getClassLoader())));
        this.videoURL         = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnailURL     = ((String) in.readValue((String.class.getClassLoader())));
    }

    public StepTO() {
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
        if ((other instanceof StepTO) == false) {
            return false;
        }
        StepTO rhs = ((StepTO) other);
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

    public static Step toModel(StepTO to) {
        Step result = new Step();
        result.setId(to.id);
        result.setDescription(to.description);
        result.setShortDescription(to.shortDescription);
        Step bugfixStep = fixBugVideoAndThumbnail(to);
        result.setThumbnailURL(bugfixStep.getThumbnailURL());
        result.setVideoURL(bugfixStep.getVideoURL());
        return result;
    }

    public static List<Step> toListModel(List<StepTO> tos) {
        List<Step> result = new ArrayList<>();
        for (StepTO to : tos) {
            result.add(toModel(to));
        }
        return result;
    }

    private static Step fixBugVideoAndThumbnail(StepTO to) {
        Step result = new Step();
        if(StringUtils.isEmpty(to.videoURL) && StringUtils.isNotEmpty(to.thumbnailURL)) {
            result.setThumbnailURL(StringUtils.EMPTY);
            result.setVideoURL(to.thumbnailURL);

        } else {
            result.setThumbnailURL(to.thumbnailURL);
            result.setVideoURL(to.videoURL);

        }
        return result;
    }
}