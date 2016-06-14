package com.moffcomm.slothstay.model;

import android.util.JsonReader;

import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-13.
 */

public class HomeHotel {

    public static final String JSON_NAME = "name";
    public static final String JSON_RATE = "rate";
    public static final String JSON_PRICE = "price";
    public static final String JSON_IMAGE_URL = "image_url";

    private String name;
    private double rate;
    private String price;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public static List<HomeHotel> fromJsonReader(JsonReader jsonReader) {
        try {
            jsonReader.beginObject();
            if (Utils.isSuccess(jsonReader) == false)
                return null;
            if (jsonReader.nextName().equals(Constants.JSON_DATA) == false) {
                return null;
            }
            jsonReader.beginArray();
            List<HomeHotel> homeHotels = new ArrayList<>();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                HomeHotel homeHotel = new HomeHotel();
                String name;
                while (jsonReader.hasNext()) {
                    name = jsonReader.nextName();
                    if (name.equals(JSON_NAME)) {
                        homeHotel.setName(jsonReader.nextString());
                    } else if (name.equals(JSON_RATE)) {
                        homeHotel.setRate(jsonReader.nextDouble());
                    } else if (name.equals(JSON_PRICE)) {
                        homeHotel.setPrice(jsonReader.nextString());
                    } else if (name.equals(JSON_IMAGE_URL)) {
                        homeHotel.setImageUrl(jsonReader.nextString());
                    }
                }
                jsonReader.endObject();
                homeHotels.add(homeHotel);
            }
            jsonReader.endArray();
            jsonReader.endObject();
            return homeHotels;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
