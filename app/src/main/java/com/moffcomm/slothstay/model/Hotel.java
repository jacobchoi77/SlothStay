package com.moffcomm.slothstay.model;

import android.util.JsonReader;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-16.
 */
public class Hotel {

    public static final String CONST_ID = "id";
    public static final String CONST_NAME = "name";
    public static final String CONST_GRADE = "grade";
    public static final String CONST_DISCOUNT = "discount";
    public static final String CONST_CHECK_IN_DATE = "check_in_date";
    public static final String CONST_CHECK_OUT_DATE = "check_out_date";
    public static final String CONST_GUEST_COUNT = "guest_count";
    public static final String CONST_RATE = "rate";
    public static final String CONST_REVIEW_COUNT = "review_count";
    public static final String CONST_SERVICE = "service";
    public static final String CONST_DESCRIPTION = "desc";
    public static final String CONST_LATITUDE = "latitude";
    public static final String CONST_LONGITUDE = "longitude";
    public static final String CONST_PICTURES = "pictures";
    public static final String CONST_ROOMS = "rooms";

    private int id;
    private String name;
    private int grade;
    private int discount;
    private Date checkInDate;
    private Date checkOutDate;
    private int guestCount;
    private double rate;
    private int reviewCount;
    private List<String> services;
    private String description;
    private String latitude;
    private String longitude;
    private List<Picture> pictures;
    private List<Room> rooms;

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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public LatLng getLatLng() {
        return new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
    }

    public static Hotel fromJsonReader(JsonReader jsonReader) {
        try {
            jsonReader.beginObject();
            Hotel hotel = new Hotel();
            while (jsonReader.hasNext()) {
                final String name = jsonReader.nextName();
                switch (name) {
                    case CONST_ID:
                        hotel.setId(jsonReader.nextInt());
                        break;
                    case CONST_NAME:
                        hotel.setName(jsonReader.nextString());
                        break;
                    case CONST_GRADE:
                        hotel.setGrade(jsonReader.nextInt());
                        break;
                    case CONST_DISCOUNT:
                        hotel.setDiscount(jsonReader.nextInt());
                        break;
                    case CONST_CHECK_IN_DATE:
                        hotel.setCheckInDate(new Date(jsonReader.nextLong()));
                        break;
                    case CONST_CHECK_OUT_DATE:
                        hotel.setCheckOutDate(new Date(jsonReader.nextLong()));
                        break;
                    case CONST_GUEST_COUNT:
                        hotel.setGuestCount(jsonReader.nextInt());
                        break;
                    case CONST_RATE:
                        hotel.setRate(jsonReader.nextDouble());
                        break;
                    case CONST_REVIEW_COUNT:
                        hotel.setReviewCount(jsonReader.nextInt());
                        break;
                    case CONST_SERVICE:
                        jsonReader.beginArray();
                        ArrayList<String> serviceList = new ArrayList<>();
                        while (jsonReader.hasNext()) {
                            serviceList.add(jsonReader.nextString());
                        }
                        hotel.setServices(serviceList);
                        jsonReader.endArray();
                        break;
                    case CONST_DESCRIPTION:
                        hotel.setDescription(jsonReader.nextString());
                        break;
                    case CONST_LATITUDE:
                        hotel.setLatitude(jsonReader.nextString());
                        break;
                    case CONST_LONGITUDE:
                        hotel.setLongitude(jsonReader.nextString());
                        break;
                    case CONST_PICTURES:
                        jsonReader.beginArray();
                        ArrayList<Picture> pictureList = new ArrayList<>();
                        while (jsonReader.hasNext()) {
                            jsonReader.beginObject();
                            pictureList.add(Picture.fromJsonReader(jsonReader));
                            jsonReader.endObject();
                        }
                        jsonReader.endArray();
                        hotel.setPictures(pictureList);
                        break;
                    case CONST_ROOMS:
                        jsonReader.beginArray();
                        ArrayList<Room> roomList = new ArrayList<>();
                        while (jsonReader.hasNext()) {
                            jsonReader.beginObject();
                            roomList.add(Room.fromJsonReader(jsonReader));
                            jsonReader.endObject();
                        }
                        jsonReader.endArray();
                        hotel.setRooms(roomList);
                        break;
                }
            }
            jsonReader.endObject();
            return hotel;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
