package com.moffcomm.slothstay.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import com.google.android.gms.maps.model.LatLng;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableThisPlease;
import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.util.Utils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-16.
 */
@ParcelablePlease(allFields = false)
public class Hotel implements Parcelable {

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
    public static final String CONST_ADDRESS_1 = "address_1";
    public static final String CONST_ADDRESS_2 = "address_2";
    public static final String CONST_PHONE = "phone";
    public static final String CONST_PICTURES = "pictures";
    public static final String CONST_ROOMS = "rooms";
    public static final String CONST_DINING = "dining";
    public static final String CONST_ROOMS_DESC = "rooms_desc";
    public static final String CONST_PROPERTY = "property";
    public static final String CONST_HIGHLIGHT = "highlight";
    public static final String CONST_AMENITIES = "amenities";
    public static final String CONST_POLICIES = "policies";

    @ParcelableThisPlease
    int id;
    @ParcelableThisPlease
    String name;
    @ParcelableThisPlease
    int grade;
    @ParcelableThisPlease
    int discount;
    @ParcelableThisPlease
    Date checkInDate;
    @ParcelableThisPlease
    Date checkOutDate;
    @ParcelableThisPlease
    int guestCount;
    @ParcelableThisPlease
    double rate;
    @ParcelableThisPlease
    int reviewCount;
    @ParcelableThisPlease
    List<String> services;
    @ParcelableThisPlease
    String description;
    @ParcelableThisPlease
    String latitude;
    @ParcelableThisPlease
    String longitude;
    @ParcelableThisPlease
    String address1;
    @ParcelableThisPlease
    String address2;
    @ParcelableThisPlease
    String phone;
    @ParcelableThisPlease
    List<Picture> pictures;
    @ParcelableThisPlease
    List<Room> rooms;
    @ParcelableThisPlease
    String dining;
    @ParcelableThisPlease
    String roomsDesc;
    @ParcelableThisPlease
    String property;
    @ParcelableThisPlease
    String highlight;
    @ParcelableThisPlease
    String amenities;
    @ParcelableThisPlease
    String policies;

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

    public String getAddress1() {
        return address1;
    }

    public String getAddress() {
        return address1 + "\n" + address2;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getDining() {
        return dining;
    }

    public void setDining(String dining) {
        this.dining = dining;
    }

    public String getRoomsDesc() {
        return roomsDesc;
    }

    public void setRoomsDesc(String roomsDesc) {
        this.roomsDesc = roomsDesc;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getPolicies() {
        return policies;
    }

    public void setPolicies(String policies) {
        this.policies = policies;
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
                        if (Constants.IS_TEST_MODE) {
                            hotel.setCheckInDate(Utils.getTodayCheckInDate());
                            jsonReader.nextLong();
                        } else
                            hotel.setCheckInDate(new Date(jsonReader.nextLong()));
                        break;
                    case CONST_CHECK_OUT_DATE:
                        if (Constants.IS_TEST_MODE) {
                            hotel.setCheckOutDate(Utils.getTomorrowCheckOutDate());
                            jsonReader.nextLong();
                        } else
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
                    case CONST_ADDRESS_1:
                        hotel.setAddress1(jsonReader.nextString());
                        break;
                    case CONST_ADDRESS_2:
                        hotel.setAddress2(jsonReader.nextString());
                        break;
                    case CONST_PHONE:
                        hotel.setPhone(jsonReader.nextString());
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
                    case CONST_ROOMS_DESC:
                        hotel.setRoomsDesc(jsonReader.nextString());
                        break;
                    case CONST_DINING:
                        hotel.setDining(jsonReader.nextString());
                        break;
                    case CONST_PROPERTY:
                        hotel.setProperty(jsonReader.nextString());
                        break;
                    case CONST_AMENITIES:
                        hotel.setAmenities(jsonReader.nextString());
                        break;
                    case CONST_HIGHLIGHT:
                        hotel.setHighlight(jsonReader.nextString());
                        break;
                    case CONST_POLICIES:
                        hotel.setPolicies(jsonReader.nextString());
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        HotelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        public Hotel createFromParcel(Parcel source) {
            Hotel target = new Hotel();
            HotelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };
}
