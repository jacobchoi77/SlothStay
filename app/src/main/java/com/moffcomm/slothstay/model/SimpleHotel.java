package com.moffcomm.slothstay.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import com.google.android.gms.maps.model.LatLng;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableThisPlease;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-13.
 */
@ParcelablePlease(allFields = false)
public class SimpleHotel implements Parcelable {

    public static final String CONST_ID = "id";
    public static final String CONST_NAME = "name";
    public static final String CONST_RATE = "rate";
    public static final String CONST_LATITUDE = "latitude";
    public static final String CONST_LONGITUDE = "longitude";
    public static final String CONST_PRICE = "price";
    public static final String CONST_IMAGE_URL = "image_url";

    @ParcelableThisPlease
    int id;
    @ParcelableThisPlease
    String name;
    @ParcelableThisPlease
    double rate;
    @ParcelableThisPlease
    String price;
    @ParcelableThisPlease
    String imageUrl;
    @ParcelableThisPlease
    String latitude;
    @ParcelableThisPlease
    String longitude;

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public LatLng getLatLng() {
        return new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
    }


    public static List<SimpleHotel> fromJsonReader(JsonReader jsonReader) {
        try {
            jsonReader.beginArray();
            List<SimpleHotel> simpleHotels = new ArrayList<>();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                SimpleHotel simpleHotel = new SimpleHotel();
                while (jsonReader.hasNext()) {
                    final String name = jsonReader.nextName();
                    switch (name) {
                        case CONST_ID:
                            simpleHotel.setId(jsonReader.nextInt());
                            break;
                        case CONST_NAME:
                            simpleHotel.setName(jsonReader.nextString());
                            break;
                        case CONST_RATE:
                            simpleHotel.setRate(jsonReader.nextDouble());
                            break;
                        case CONST_LATITUDE:
                            simpleHotel.setLatitude(jsonReader.nextString());
                            break;
                        case CONST_LONGITUDE:
                            simpleHotel.setLongitude(jsonReader.nextString());
                            break;
                        case CONST_PRICE:
                            simpleHotel.setPrice(jsonReader.nextString());
                            break;
                        case CONST_IMAGE_URL:
                            simpleHotel.setImageUrl(jsonReader.nextString());
                            break;
                    }
                }
                jsonReader.endObject();
                simpleHotels.add(simpleHotel);
            }
            jsonReader.endArray();
            return simpleHotels;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        SimpleHotelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<SimpleHotel> CREATOR = new Creator<SimpleHotel>() {
        public SimpleHotel createFromParcel(Parcel source) {
            SimpleHotel target = new SimpleHotel();
            SimpleHotelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public SimpleHotel[] newArray(int size) {
            return new SimpleHotel[size];
        }
    };
}
