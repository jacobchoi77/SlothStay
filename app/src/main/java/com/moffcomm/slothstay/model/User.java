package com.moffcomm.slothstay.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableThisPlease;

import java.io.IOException;

/**
 * Created by jacobsFactory on 2016-07-04.
 */
@ParcelablePlease(allFields = false)
public class User implements Parcelable {

    public static final String CONST_FIRST_NAME = "first_name";
    public static final String CONST_LAST_NAME = "last_name";
    public static final String CONST_ID = "id";
    public static final String CONST_MILEAGE = "mileage";
    public static final String CONST_EMAIL = "email";
    public static final String CONST_PHONE = "phone";

    @ParcelableThisPlease
    String firstName;
    @ParcelableThisPlease
    String lastName;
    @ParcelableThisPlease
    String id;
    @ParcelableThisPlease
    int mileage;
    @ParcelableThisPlease
    String email;
    @ParcelableThisPlease
    String phone;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        UserParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static User fromJsonReader(JsonReader jsonReader) {
        try {
            jsonReader.beginObject();
            User user = new User();
            while (jsonReader.hasNext()) {
                final String name = jsonReader.nextName();
                switch (name) {
                    case CONST_FIRST_NAME:
                        user.setFirstName(jsonReader.nextString());
                        break;
                    case CONST_LAST_NAME:
                        user.setLastName(jsonReader.nextString());
                        break;
                    case CONST_ID:
                        user.setId(jsonReader.nextString());
                        break;
                    case CONST_EMAIL:
                        user.setEmail(jsonReader.nextString());
                        break;
                    case CONST_MILEAGE:
                        user.setMileage(jsonReader.nextInt());
                        break;
                    case CONST_PHONE:
                        user.setPhone(jsonReader.nextString());
                        break;
                }
            }
            jsonReader.endObject();
            return user;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            User target = new User();
            UserParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
