package com.moffcomm.slothstay.model;

import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by jacobsFactory on 2016-06-16.
 */
public class Picture {
    public static final String CONST_IMAGE_URL = "image_url";
    public static final String CONST_DESCRIPTION = "desc";

    private String imageUrl;
    private String description;

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
}
