package com.moffcomm.slothstay;

import android.app.Application;

import com.moffcomm.slothstay.model.Reservation;
import com.moffcomm.slothstay.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-07-04.
 */
public class SlothStayApplication extends Application {

    private User user;
    private List<Reservation> reservationList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void addReservation(Reservation reservation) {
        if (reservationList == null) {
            reservationList = new ArrayList<>();
        }
        reservationList.add(reservation);
    }
}
