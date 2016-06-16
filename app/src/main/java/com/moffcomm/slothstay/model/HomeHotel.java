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

    public static final String CONST_ID = "id";
    public static final String CONST_NAME = "name";
    public static final String CONST_RATE = "rate";
    public static final String CONST_PRICE = "price";
    public static final String CONST_IMAGE_URL = "image_url";

    private int id;
    private String name;
    private double rate;
    private String price;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
            jsonReader.beginArray();
            List<HomeHotel> homeHotels = new ArrayList<>();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                HomeHotel homeHotel = new HomeHotel();
                String name;
                while (jsonReader.hasNext()) {
                    name = jsonReader.nextName();
                    if (name.equals(CONST_ID)) {
                        homeHotel.setId(jsonReader.nextInt());
                    } else if (name.equals(CONST_NAME)) {
                        homeHotel.setName(jsonReader.nextString());
                    } else if (name.equals(CONST_RATE)) {
                        homeHotel.setRate(jsonReader.nextDouble());
                    } else if (name.equals(CONST_PRICE)) {
                        homeHotel.setPrice(jsonReader.nextString());
                    } else if (name.equals(CONST_IMAGE_URL)) {
                        homeHotel.setImageUrl(jsonReader.nextString());
                    }
                }
                jsonReader.endObject();
                homeHotels.add(homeHotel);
            }
            jsonReader.endArray();
            return homeHotels;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
