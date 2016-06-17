package com.moffcomm.slothstay.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.Hotel;
import com.moffcomm.slothstay.ui.adapter.HotelPictureAdapter;
import com.moffcomm.slothstay.util.Utils;

import java.lang.ref.WeakReference;

public class HotelActivity extends AppCompatActivity {

    private Hotel hotel;
    private int hotelId;
    private GetHotelAsyncTask mAsyncTask;
    private ViewPager pictureViewPager;
    private HotelPictureAdapter hotelPictureAdapter;
    private CollapsingToolbarLayout collapsing_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        hotelId = getIntent().getIntExtra(Hotel.CONST_ID, -1);
        if (hotelId == -1) {
            finish();
        }
        pictureViewPager = (ViewPager) findViewById(R.id.pictureViewPager);
        collapsing_container = (CollapsingToolbarLayout) findViewById(R.id.collapsing_container);
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        setContents();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAsyncTask != null && mAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            mAsyncTask.cancel(true);
        }
        mAsyncTask = new GetHotelAsyncTask(this);
        mAsyncTask.execute(hotelId);
    }

    public void setContents() {
        hotelPictureAdapter = new HotelPictureAdapter(this, hotel.getPictures());
        pictureViewPager.setAdapter(hotelPictureAdapter);
        collapsing_container.setTitle(hotel.getName());
    }

    private static class GetHotelAsyncTask extends AsyncTask<Integer, Void, Hotel> {

        private WeakReference<HotelActivity> homeActivityWeakReference;

        public GetHotelAsyncTask(HotelActivity hotelActivity) {
            homeActivityWeakReference = new WeakReference<>(hotelActivity);
        }

        @Override
        protected Hotel doInBackground(Integer... params) {
            HotelActivity hotelActivity = homeActivityWeakReference.get();
            if (hotelActivity != null)
                return Hotel.fromJsonReader(Utils.getJsonReader(hotelActivity,
                        hotelActivity.getString(R.string.what_hotel) + "_" + params[0]));
            return null;
        }

        @Override
        protected void onPostExecute(Hotel hotel) {
            super.onPostExecute(hotel);
            if (hotel != null) {
                HotelActivity hotelActivity = homeActivityWeakReference.get();
                if (hotelActivity != null)
                    hotelActivity.setHotel(hotel);
            }

        }
    }
}
