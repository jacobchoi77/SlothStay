package com.moffcomm.slothstay.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableThisPlease;
import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-07-06.
 */
@ParcelablePlease(allFields = false)
public class Reservation implements Parcelable {
    public static final String CONST_HOTEL_NAME = "hotel_name";
    public static final String CONST_IMAGE_URL = "image_url";
    public static final String CONST_HOTEL_ADDRESS = "hotel_address";
    public static final String CONST_HOTEL_PHONE = "hotel_phone";
    public static final String CONST_GUEST_COUNT = "guest_count";
    public static final String CONST_CHECK_IN_DATE = "check_in_date";
    public static final String CONST_CHECK_OUT_DATE = "check_out_date";
    public static final String CONST_LATITUDE = "latitude";
    public static final String CONST_LONGITUDE = "longitude";
    public static final String CONST_ROOM_NAME = "room_name";
    public static final String CONST_ITINERARY = "itinerary";


    @ParcelableThisPlease
    String hotelName;
    @ParcelableThisPlease
    String imageUrl;
    @ParcelableThisPlease
    String hotelAddress;
    @ParcelableThisPlease
    String hotelPhone;
    @ParcelableThisPlease
    int guestCount;
    @ParcelableThisPlease
    Date checkInDate;
    @ParcelableThisPlease
    Date checkOutDate;
    @ParcelableThisPlease
    String latitude;
    @ParcelableThisPlease
    String longitude;
    @ParcelableThisPlease
    String roomName;
    @ParcelableThisPlease
    String itinerary;
    @ParcelableThisPlease
    Book book;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getHotelPhone() {
        return hotelPhone;
    }

    public void setHotelPhone(String hotelPhone) {
        this.hotelPhone = hotelPhone;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public static List<Reservation> fromJsonReader(JsonReader jsonReader) {
        try {
            List<Reservation> reservationList = new ArrayList<>();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                Reservation reservation = new Reservation();
                while (jsonReader.hasNext()) {
                    final String name = jsonReader.nextName();
                    switch (name) {
                        case CONST_HOTEL_NAME:
                            reservation.setHotelName(jsonReader.nextString());
                            break;
                        case CONST_IMAGE_URL:
                            reservation.setImageUrl(jsonReader.nextString());
                            break;
                        case CONST_HOTEL_PHONE:
                            reservation.setHotelPhone(jsonReader.nextString());
                            break;
                        case CONST_GUEST_COUNT:
                            reservation.setGuestCount(jsonReader.nextInt());
                            break;
                        case CONST_CHECK_IN_DATE:
                            reservation.setCheckInDate(new Date(jsonReader.nextLong()));
                            break;
                        case CONST_CHECK_OUT_DATE:
                            reservation.setCheckOutDate(new Date(jsonReader.nextLong()));
                            break;
                        case CONST_LATITUDE:
                            reservation.setLatitude(jsonReader.nextString());
                            break;
                        case CONST_LONGITUDE:
                            reservation.setLongitude(jsonReader.nextString());
                            break;
                        case CONST_ROOM_NAME:
                            reservation.setRoomName(jsonReader.nextString());
                            break;
                        case CONST_ITINERARY:
                            reservation.setItinerary(jsonReader.nextString());
                            break;
                        case CONST_HOTEL_ADDRESS:
                            reservation.setHotelAddress(jsonReader.nextString());
                            break;
                    }
                }
                reservationList.add(reservation);
                jsonReader.endObject();
            }
            jsonReader.endArray();
            return reservationList;
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
        ReservationParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        public Reservation createFromParcel(Parcel source) {
            Reservation target = new Reservation();
            ReservationParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };
}