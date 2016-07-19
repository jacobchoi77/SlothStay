package com.moffcomm.slothstay.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.Book;
import com.moffcomm.slothstay.model.Hotel;
import com.moffcomm.slothstay.model.Room;
import com.moffcomm.slothstay.ui.adapter.HotelPictureAdapter;
import com.moffcomm.slothstay.util.Utils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;

public class HotelActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, AppBarLayout.OnOffsetChangedListener {

    private Hotel hotel;
    private int hotelId;
    private GetHotelAsyncTask mAsyncTask;
    private ViewPager pictureViewPager;
    private HotelPictureAdapter hotelPictureAdapter;
    private GoogleMap mMap;
    private LinearLayout linearLayout;
    private View selectedRoomView;
    private SlidingUpPanelLayout mLayout;
    private View roomSelectButton;
    private View roomInfoRelativeLayout;
    private ScrollView scrollView;
    private TextView picDescTextView;

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
                onBackPressed();
            }
        });
        hotelId = getIntent().getIntExtra(Hotel.CONST_ID, -1);
        if (hotelId == -1) {
            finish();
        }
        pictureViewPager = (ViewPager) findViewById(R.id.pictureViewPager);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        roomSelectButton = findViewById(R.id.selectRoomButton);
        roomInfoRelativeLayout = findViewById(R.id.roomInfoRelativeLayout);
        picDescTextView = (TextView) findViewById(R.id.picDescTextView);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    roomInfoRelativeLayout.setVisibility(View.GONE);
                    roomSelectButton.setVisibility(View.GONE);
                    picDescTextView.setVisibility(View.GONE);
                } else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    picDescTextView.setVisibility(View.VISIBLE);
                    roomInfoRelativeLayout.setVisibility(View.GONE);
                    roomSelectButton.setVisibility(View.GONE);
                } else {
                    picDescTextView.setVisibility(View.GONE);
                    roomInfoRelativeLayout.setVisibility(View.VISIBLE);
                    roomSelectButton.setVisibility(View.VISIBLE);
                }
            }
        });
        scrollView = (ScrollView) findViewById(R.id.scrollView);
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
        getSupportActionBar().setTitle(hotel.getName());
        hotelPictureAdapter = new HotelPictureAdapter(this, hotel.getPictures());
        pictureViewPager.setAdapter(hotelPictureAdapter);
        pictureViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                picDescTextView.setText(hotel.getPictures().get(position).getDescription());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        addRooms();
        addOthers();
        linearLayout.getChildAt(0).performClick();
    }

    private void addOthers() {
        LayoutInflater inflater = getLayoutInflater();
        if (hotel.getDining() != null) {
            View view = inflater.inflate(R.layout.item_others, linearLayout, false);
            ((TextView) view.findViewById(R.id.titleTextView)).setText(R.string.dining);
            ((TextView) view.findViewById(R.id.contentTextView)).setText(hotel.getDining());
            linearLayout.addView(view);
        }
        if (hotel.getRoomsDesc() != null) {
            View view = inflater.inflate(R.layout.item_others, linearLayout, false);
            ((TextView) view.findViewById(R.id.titleTextView)).setText(R.string.rooms_desc);
            ((TextView) view.findViewById(R.id.contentTextView)).setText(hotel.getRoomsDesc());
            linearLayout.addView(view);
        }
        if (hotel.getProperty() != null) {
            View view = inflater.inflate(R.layout.item_others, linearLayout, false);
            ((TextView) view.findViewById(R.id.titleTextView)).setText(R.string.property);
            ((TextView) view.findViewById(R.id.contentTextView)).setText(hotel.getProperty());
            linearLayout.addView(view);
        }
        if (hotel.getPolicies() != null) {
            View view = inflater.inflate(R.layout.item_others, linearLayout, false);
            ((TextView) view.findViewById(R.id.titleTextView)).setText(R.string.policies);
            ((TextView) view.findViewById(R.id.contentTextView)).setText(hotel.getPolicies());
            linearLayout.addView(view);
        }
        if (hotel.getAmenities() != null) {
            View view = inflater.inflate(R.layout.item_others, linearLayout, false);
            ((TextView) view.findViewById(R.id.titleTextView)).setText(R.string.amenities);
            ((TextView) view.findViewById(R.id.contentTextView)).setText(hotel.getAmenities());
            linearLayout.addView(view);
        }
        if (hotel.getHighlight() != null) {
            View view = inflater.inflate(R.layout.item_others, linearLayout, false);
            ((TextView) view.findViewById(R.id.titleTextView)).setText(R.string.highlights);
            ((TextView) view.findViewById(R.id.contentTextView)).setText(hotel.getHighlight());
            linearLayout.addView(view);
        }
    }

    private void addRooms() {
        for (Room room : hotel.getRooms()) {
            addRoom(room);
        }
    }

    private void addRoom(Room room, int index) {
        View view = getLayoutInflater().inflate(R.layout.item_room, linearLayout, false);
        ((TextView) view.findViewById(R.id.nameTextView)).setText(room.getName());
        ((TextView) view.findViewById(R.id.priceTextView)).setText(room.getPrice());
        (view.findViewById(R.id.bookButton)).setTag(room);
        Glide.with(this).load(room.getImageUrl()).into((ImageView) view.findViewById(R.id.imageView));
        view.setOnClickListener(this);
        if (index != -1)
            linearLayout.addView(view, index);
        else
            linearLayout.addView(view);
    }

    private void addRoom(Room room) {
        addRoom(room, -1);
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

    @Override
    public void onClick(View v) {
        if (v != selectedRoomView) {
            CardView cardView = (CardView) getLayoutInflater().inflate(R.layout.item_card_room, linearLayout, false);
            int index = linearLayout.indexOfChild(v);
            linearLayout.removeView(v);
            v.setOnClickListener(null);
            cardView.addView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            v.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            cardView.setOnClickListener(this);
            linearLayout.addView(cardView, index);
            if (selectedRoomView != null) {
                index = linearLayout.indexOfChild(selectedRoomView);
                linearLayout.removeView(selectedRoomView);
                addRoom(hotel.getRooms().get(index), index);
            }
            selectedRoomView = cardView;
        }
    }

    public void onSelectRoomClick(View v) {
        scrollView.smoothScrollTo(0, linearLayout.getTop());
    }

    public void onBookClick(View view) {
        Room room = (Room) view.getTag();
        Book book = new Book();
        book.setHotel(hotel);
        book.setRoom(room);
        book.setGuestCount(1);
        book.setCheckInDate(hotel.getCheckInDate());
        book.setCheckOutDate(hotel.getCheckOutDate());
        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

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
