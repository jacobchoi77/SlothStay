package com.moffcomm.slothstay.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableThisPlease;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-16.
 */
@ParcelablePlease(allFields = false)
public class Room implements Parcelable {

    public static final String CONST_NAME = "name";
    public static final String CONST_PRICE = "price";
    public static final String CONST_ETC = "etc";
    public static final String CONST_DETAIL = "detail";
    public static final String CONST_IMAGE_URL = "image_url";
    public static final String CONST_CHECK_IN_EARLY = "check_in_early";
    public static final String CONST_CHECK_IN_LATE = "check_in_late";
    public static final String CONST_CHECK_OUT_EARLY = "check_out_early";
    public static final String CONST_CHECK_OUT_LATE = "check_out_late";

    @ParcelableThisPlease
    String name;
    @ParcelableThisPlease
    String price;
    @ParcelableThisPlease
    List<String> etc;
    @ParcelableThisPlease
    String detail;
    @ParcelableThisPlease
    String imageUrl;
    @ParcelableThisPlease
    int checkInEarly;
    @ParcelableThisPlease
    int checkInLate;
    @ParcelableThisPlease
    int checkOutEarly;
    @ParcelableThisPlease
    int checkOutLate;

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

    public int getCheckInEarly() {
        return checkInEarly;
    }

    public void setCheckInEarly(int checkInEarly) {
        this.checkInEarly = checkInEarly;
    }

    public int getCheckInLate() {
        return checkInLate;
    }

    public void setCheckInLate(int checkInLate) {
        this.checkInLate = checkInLate;
    }

    public int getCheckOutEarly() {
        return checkOutEarly;
    }

    public void setCheckOutEarly(int checkOutEarly) {
        this.checkOutEarly = checkOutEarly;
    }

    public int getCheckOutLate() {
        return checkOutLate;
    }

    public void setCheckOutLate(int checkOutLate) {
        this.checkOutLate = checkOutLate;
    }

    public static Room fromJsonReader(JsonReader jsonReader) {
        try {
            Room room = new Room();
            while (jsonReader.hasNext()) {
                final String name = jsonReader.nextName();
                switch (name) {
                    case CONST_NAME:
                        room.setName(jsonReader.nextString());
                        break;
                    case CONST_PRICE:
                        room.setPrice(jsonReader.nextString());
                        break;
                    case CONST_ETC:
                        jsonReader.beginArray();
                        ArrayList<String> etcList = new ArrayList<>();
                        while (jsonReader.hasNext()) {
                            etcList.add(jsonReader.nextString());
                        }
                        room.setEtc(etcList);
                        jsonReader.endArray();
                        break;
                    case CONST_DETAIL:
                        room.setDetail(jsonReader.nextString());
                        break;
                    case CONST_IMAGE_URL:
                        room.setImageUrl(jsonReader.nextString());
                        break;
                    case CONST_CHECK_IN_EARLY:
                        room.setCheckInEarly(jsonReader.nextInt());
                        break;
                    case CONST_CHECK_IN_LATE:
                        room.setCheckInLate(jsonReader.nextInt());
                        break;
                    case CONST_CHECK_OUT_EARLY:
                        room.setCheckOutEarly(jsonReader.nextInt());
                        break;
                    case CONST_CHECK_OUT_LATE:
                        room.setCheckOutLate(jsonReader.nextInt());
                        break;

                }
            }
            return room;
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
        RoomParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        public Room createFromParcel(Parcel source) {
            Room target = new Room();
            RoomParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Room[] newArray(int size) {
            return new Room[size];
        }
    };
}
