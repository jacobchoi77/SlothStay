package com.moffcomm.slothstay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.SlothStayApplication;
import com.moffcomm.slothstay.model.Book;
import com.moffcomm.slothstay.model.User;
import com.tripadvisor.seekbar.ClockView;

import org.joda.time.DateTime;

public class CheckActivity extends AppCompatActivity {

    private Book book;
    private ClockView checkInClockView;
    private ClockView checkOutClockView;
    private DateTime checkInDate;
    private DateTime checkOutDate;
    private TextView checkInTextView;
    private TextView checkOutTextView;
    private DateTime hotelCheckInDate;
    private DateTime hotelCheckOutDate;
    private TextView checkInMileageTextView;
    private TextView checkOutMileageTextView;
    private TextView totalMileageTextView;
    private User user;
    private int checkInMileage;
    private int checkOutMileage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        user = ((SlothStayApplication) getApplication()).getUser();
        if (user == null) {
            finish();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        book = getIntent().getParcelableExtra("book");

        checkInClockView = (ClockView) findViewById(R.id.checkInClockView);
        checkOutClockView = (ClockView) findViewById(R.id.checkOutClockView);

        hotelCheckInDate = new DateTime(book.getHotel().getCheckInDate());
        hotelCheckOutDate = new DateTime(book.getHotel().getCheckOutDate());

        checkInDate = new DateTime(book.getCheckInDate());
        checkOutDate = new DateTime(book.getCheckOutDate());

        DateTime checkInMinDate = hotelCheckInDate.minusHours(book.getRoom().getCheckInEarly());
        DateTime checkInMaxDate = hotelCheckInDate.plusHours(book.getRoom().getCheckInLate());
        checkInClockView.setBounds(checkInMinDate, checkInMaxDate, true);
        checkInClockView.setNewCurrentTime(checkInDate);

        DateTime checkOutMinDate = hotelCheckOutDate.minusHours(book.getRoom().getCheckOutEarly());
        DateTime checkOutMaxDate = hotelCheckOutDate.plusHours(book.getRoom().getCheckOutLate());
        checkOutClockView.setBounds(checkOutMinDate, checkOutMaxDate, true);
        checkOutClockView.setNewCurrentTime(checkOutDate);

        checkInTextView = (TextView) findViewById(R.id.checkInTextView);
        checkOutTextView = (TextView) findViewById(R.id.checkOutTextView);

        checkInMileageTextView = (TextView) findViewById(R.id.checkInMileageTextView);
        checkOutMileageTextView = (TextView) findViewById(R.id.checkOutMileageTextView);
        totalMileageTextView = (TextView) findViewById(R.id.totalMileageTextView);

        checkInClockView.setClockTimeUpdateListener(new ClockView.ClockTimeUpdateListener() {
            @Override
            public void onClockTimeUpdate(ClockView clockView, DateTime currentTime) {
                int res = 0;
                checkInDate = currentTime;
                if (hotelCheckInDate.compareTo(checkInDate) == 0) {
                    res = R.string.check_in_normal;
                    checkInMileageTextView.setText("");
                    checkInMileage = 0;
                } else if (hotelCheckInDate.compareTo(checkInDate) < 0) {
                    res = R.string.check_in_late;
                    checkInMileage = calculateCheckInMileage(
                            checkInDate.getHourOfDay() - hotelCheckInDate.getHourOfDay());
                    checkInMileageTextView.setText("+" + checkInMileage);
                } else {
                    res = R.string.check_in_early;
                    checkInMileage = calculateCheckInMileage(
                            checkInDate.getHourOfDay() - hotelCheckInDate.getHourOfDay());
                    checkInMileageTextView.setText("-" + checkInMileage);
                }
                checkInTextView.setText(getString(res, checkInDate.getHourOfDay()));
                totalMileageTextView.setText("" + calculateTotalMileage());
            }
        });

        checkOutClockView.setClockTimeUpdateListener(new ClockView.ClockTimeUpdateListener() {
            @Override
            public void onClockTimeUpdate(ClockView clockView, DateTime currentTime) {
                int res = 0;
                checkOutDate = currentTime;
                if (hotelCheckOutDate.compareTo(checkOutDate) == 0) {
                    res = R.string.check_out_normal;
                    checkOutMileageTextView.setText("");
                    checkOutMileage = 0;
                } else if (hotelCheckOutDate.compareTo(checkOutDate) < 0) {
                    res = R.string.check_out_late;
                    checkOutMileage = calculateCheckOutMileage(
                            hotelCheckOutDate.getHourOfDay() - checkOutDate.getHourOfDay());
                    checkOutMileageTextView.setText("-" + checkOutMileage);
                } else {
                    res = R.string.check_out_early;
                    checkOutMileage = calculateCheckOutMileage(
                            hotelCheckOutDate.getHourOfDay() - checkOutDate.getHourOfDay());
                    checkOutMileageTextView.setText("+" + checkOutMileage);
                }
                checkOutTextView.setText(getString(res, checkOutDate.getHourOfDay()));
                totalMileageTextView.setText("" + calculateTotalMileage());
            }
        });

        ((TextView) findViewById(R.id.currentMileageTextView)).setText("" + user.getMileage());
        ((TextView) findViewById(R.id.totalMileageTextView)).setText("" + user.getMileage());
    }

    @Override
    public void onBackPressed() {
        book.setCheckInDate(checkInDate.toDate());
        book.setCheckOutDate(checkOutDate.toDate());
        Intent intent = new Intent();
        intent.putExtra("book", book);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private int calculateCheckInMileage(int hour) {
        return hour * 10;
    }

    private int calculateCheckOutMileage(int hour) {
        return hour * 10;
    }

    private int calculateTotalMileage() {
        return user.getMileage() + checkInMileage + checkOutMileage;
    }

}
