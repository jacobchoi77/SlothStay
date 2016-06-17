package com.moffcomm.slothstay.ui;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.Hotel;
import com.moffcomm.slothstay.ui.adapter.HotelPictureAdapter;
import com.moffcomm.slothstay.util.Utils;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.Locale;

public class HotelActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Hotel hotel;
    private int hotelId;
    private GetHotelAsyncTask mAsyncTask;
    private ViewPager pictureViewPager;
    private HotelPictureAdapter hotelPictureAdapter;
    private CollapsingToolbarLayout collapsing_container;
    private GoogleMap mMap;

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
        ((TextView) findViewById(R.id.priceTextView)).setText(hotel.getRooms().get(0).getPrice());
        final String checkIn = Utils.getDateString(hotel.getCheckInDate(), getString(R.string.hotel_date_format));
        final String checkOut = Utils.getDateString(hotel.getCheckOutDate(), getString(R.string.hotel_date_format));
        final int guest = hotel.getGuestCount();
        final String total = getString(R.string.hotel_date_guest, checkIn, checkOut, guest);
        ((TextView) findViewById(R.id.dayGuestTextView)).setText(total);
        final String view = NumberFormat.getNumberInstance().format(hotel.getReviewCount());
        ((TextView) findViewById(R.id.reviewTextView)).setText(getString(R.string.view, view));
        ((TextView) findViewById(R.id.descTextView)).setText(hotel.getDescription());
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarker();
        final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation") // We use the new method when supported
                @SuppressLint("NewApi") // We check which build version we are using.
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    showHotel();
                }
            });
        }
    }

    public void showHotel() {
        if (mMap == null) {
            return;
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hotel.getLatLng(), Constants.HOTEL_ZOOM));
    }

    private void addMarker() {
        mMap.addMarker(new MarkerOptions()
                .position(hotel.getLatLng())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
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
