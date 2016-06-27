package com.moffcomm.slothstay.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableThisPlease;

/**
 * Created by jacobsFactory on 2016-06-22.
 */
@ParcelablePlease(allFields = false)
public class Book implements Parcelable {
    @ParcelableThisPlease
    Hotel hotel;
    @ParcelableThisPlease
    Room room;
    @ParcelableThisPlease
    long checkInDateInMillis;
    @ParcelableThisPlease
    long checkOutDateInMillis;
    @ParcelableThisPlease
    int guestCount;

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public long getCheckInDateInMillis() {
        return checkInDateInMillis;
    }

    public void setCheckInDateInMillis(long checkInDateInMillis) {
        this.checkInDateInMillis = checkInDateInMillis;
    }

    public long getCheckOutDateInMillis() {
        return checkOutDateInMillis;
    }

    public void setCheckOutDateInMillis(long checkOutDateInMillis) {
        this.checkOutDateInMillis = checkOutDateInMillis;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        BookParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            Book target = new Book();
            BookParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
