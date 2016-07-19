package com.moffcomm.slothstay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.Book;
import com.moffcomm.slothstay.model.Reservation;

import java.util.Random;


public class BookActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CHECK = 1;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        book = (Book) (getIntent().getParcelableExtra("book"));
        setupContent();
    }

    private void setupContent() {
        ((TextView) findViewById(R.id.hotelNameTextView)).setText(book.getHotel().getName());
        ((TextView) findViewById(R.id.address1TextView)).setText(book.getHotel().getAddress1());
        ((TextView) findViewById(R.id.address2TextView)).setText(book.getHotel().getAddress2());
        ((TextView) findViewById(R.id.roomNameTextView)).setText(book.getRoom().getName());
        ((TextView) findViewById(R.id.priceTextView)).setText(book.getRoom().getPrice());
        Glide.with(this).load(book.getHotel().getPictures().get(0).getImageUrl()).
                into((ImageView) findViewById(R.id.imageView));
        int res = 0;
        if (book.getHotel().getCheckInDate().compareTo(book.getCheckInDate()) == 0) {
            res = R.string.check_in_normal;
        } else if (book.getHotel().getCheckInDate().compareTo(book.getCheckInDate()) < 0) {
            res = R.string.check_in_late;
        } else {
            res = R.string.check_in_early;
        }
        ((TextView) findViewById(R.id.checkInTextView)).setText(getString(res, book.getCheckInDate().getHours()));

        if (book.getHotel().getCheckOutDate().compareTo(book.getCheckOutDate()) == 0) {
            res = R.string.check_out_normal;
        } else if (book.getHotel().getCheckOutDate().compareTo(book.getCheckOutDate()) < 0) {
            res = R.string.check_out_late;
        } else {
            res = R.string.check_out_early;
        }
        ((TextView) findViewById(R.id.checkOutTextView)).setText(getString(res, book.getCheckOutDate().getHours()));

    }

    public void onCheckClick(View view) {
        Intent intent = new Intent(this, CheckActivity.class);
        intent.putExtra("book", book);
        startActivityForResult(intent, REQUEST_CODE_CHECK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHECK && resultCode == RESULT_OK) {
            book = data.getParcelableExtra("book");
            setupContent();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_next) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void onPayClick(View v) {
        Reservation reservation = new Reservation();
        reservation.setItinerary("7194824212742");
        reservation.setImageUrl(book.getHotel().getPictures().get(0).getImageUrl());
        reservation.setRoomName(book.getRoom().getName());
        reservation.setCheckInDate(book.getCheckInDate());
        reservation.setCheckOutDate(book.getCheckOutDate());
        reservation.setGuestCount(book.getGuestCount());
        reservation.setHotelAddress(book.getHotel().getAddress());
        reservation.setHotelName(book.getHotel().getName());
        reservation.setHotelPhone(book.getHotel().getPhone());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("reservation", reservation);
        startActivity(intent);
    }
}
