package com.moffcomm.slothstay.model;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-16.
 */
public class Room {

    public static final String CONST_NAME = "name";
    public static final String CONST_PRICE = "price";
    public static final String CONST_ETC = "etc";
    public static final String CONST_DETAIL = "detail";
    public static final String CONST_IMAGE_URL = "image_url";

    private String name;
    private String price;
    private List<String> etc;
    private String detail;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getEtc() {
        return etc;
    }

    public void setEtc(List<String> etc) {
        this.etc = etc;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Room fromJsonReader(JsonReader jsonReader) {
        try {
            Room room = new Room();
            String name;
            while (jsonReader.hasNext()) {
                name = jsonReader.nextName();
                if (name.equals(CONST_NAME)) {
                    room.setName(jsonReader.nextString());
                } else if (name.equals(CONST_PRICE)) {
                    room.setPrice(jsonReader.nextString());
                } else if (name.equals(CONST_ETC)) {
                    jsonReader.beginArray();
                    ArrayList<String> etcList = new ArrayList<>();
                    while (jsonReader.hasNext()) {
                        etcList.add(jsonReader.nextString());
                    }
                    room.setEtc(etcList);
                    jsonReader.endArray();
                } else if (name.equals(CONST_DETAIL)) {
                    room.setDetail(jsonReader.nextString());
                } else if (name.equals(CONST_IMAGE_URL)) {
                    room.setImageUrl(jsonReader.nextString());
                }
            }
            return room;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
