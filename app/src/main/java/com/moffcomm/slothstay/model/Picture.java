package com.moffcomm.slothstay.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableThisPlease;

import java.io.IOException;

/**
 * Created by jacobsFactory on 2016-06-16.
 */
@ParcelablePlease(allFields = false)
public class Picture implements Parcelable {
    public static final String CONST_IMAGE_URL = "image_url";
    public static final String CONST_DESCRIPTION = "desc";

    @ParcelableThisPlease
    String imageUrl;
    @ParcelableThisPlease
    String description;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Picture fromJsonReader(JsonReader jsonReader) {
        try {
            Picture picture = new Picture();
            while (jsonReader.hasNext()) {
                final String name = jsonReader.nextName();
                switch (name) {
                    case CONST_IMAGE_URL:
                        picture.setImageUrl(jsonReader.nextString());
                        break;
                    case CONST_DESCRIPTION:
                        picture.setDescription(jsonReader.nextString());
                        break;
                }
            }
            return picture;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PictureParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        public Picture createFromParcel(Parcel source) {
            Picture target = new Picture();
            PictureParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}
