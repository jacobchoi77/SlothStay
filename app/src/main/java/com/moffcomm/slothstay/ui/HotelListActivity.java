package com.moffcomm.slothstay.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.SimpleHotel;
import com.moffcomm.slothstay.ui.adapter.HotelListAdapter;
import com.moffcomm.slothstay.util.Utils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HotelListActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;
    private GoogleMap mMap;
    private List<SimpleHotel> simpleHotels = new ArrayList<>();
    private GetSimpleHotelAsyncTask mAsyncTask;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SlidingUpPanelLayout mLayout;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;
    private float oldSlideOffset = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HotelListAdapter(simpleHotels, this);
        mRecyclerView.setAdapter(mAdapter);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (oldSlideOffset == 0) {
                    oldSlideOffset = slideOffset;
                    return;
                } else if (slideOffset < oldSlideOffset) {
                    oldSlideOffset = slideOffset;
                    showSnackBar();
                } else {
                    oldSlideOffset = slideOffset;
                    hideSnackBar();
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    fab.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.fab_list);
                    hideSnackBar();
                } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    fab.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.fab_map);
                    showSnackBar();
                } else if (newState == SlidingUpPanelLayout.PanelState.ANCHORED) {
                    showSnackBar();
                } else {
                    fab.setVisibility(View.GONE);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                    mRecyclerView.scrollToPosition(0);
                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            if (mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
                                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                            }
                            mMap.setOnMapClickListener(null);
                        }
                    });
                } else {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    mMap.setOnMapClickListener(null);
                }

            }
        });
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0)
                    showSnackBar();
                else
                    hideSnackBar();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAsyncTask != null && mAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            mAsyncTask.cancel(true);
        }
        mAsyncTask = new GetSimpleHotelAsyncTask(this);
        mAsyncTask.execute();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    public void setSimpleHotels(List<SimpleHotel> simpleHotels) {
        this.simpleHotels.clear();
        this.simpleHotels.addAll(simpleHotels);
        mAdapter.notifyDataSetChanged();
        ((TextView) findViewById(R.id.todaysHotelTextView)).setText(
                getString(R.string.hotel_list_head_title, simpleHotels.size()));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (mMapView.getViewTreeObserver().isAlive()) {
            mMapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation") // We use the new method when supported
                @SuppressLint("NewApi") // We check which build version we are using.
                @Override
                public void onGlobalLayout() {
                    if (simpleHotels != null && simpleHotels.size() > 0) {
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (SimpleHotel simpleHotel : simpleHotels) {
                            builder.include(simpleHotel.getLatLng());
                        }
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            mMapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            mMapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 200));
                        showHotels();
                        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {
                                if (mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
                                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                }
                                mMap.setOnMapClickListener(null);
                            }
                        });
                    }
                }
            });
        }

    }

    private void showHotels() {
        if (mMap == null) {
            return;
        }
        for (SimpleHotel simpleHotel : simpleHotels) {
            mMap.addMarker(new MarkerOptions()
                    .position(simpleHotel.getLatLng()).icon(BitmapDescriptorFactory.fromBitmap(
                            Utils.getMarkerBitmapFromView(this, simpleHotel.getPrice()))));
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Calendar calendar = new GregorianCalendar();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tommorrow = calendar.getTime();
        String s = getString(R.string.hotel_list_info, Utils.getDateString(today, getString(R.string.hotel_date_format)),
                Utils.getDateString(tommorrow, getString(R.string.hotel_date_format)));
        ((TextView) toolbar.findViewById(R.id.infoTextView)).setText(s);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showSnackBar() {
        if (snackbar != null && snackbar.isShown())
            return;
        snackbar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(Color.WHITE);
        layout.setGravity(Gravity.CENTER);
        layout.setElevation(10);
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setText(getString(R.string.sort_filter));
        textView.setTextSize(16);
        textView.setTextColor(Color.BLACK);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sort, 0, 0, 0);
        textView.setCompoundDrawablePadding(10);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.weight = 0;
        textView.setLayoutParams(layoutParams);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelListActivity.this, SortFilterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
            }
        });
        snackbar.show();
    }

    private void hideSnackBar() {
        if (snackbar != null)
            snackbar.dismiss();
    }

    private static class GetSimpleHotelAsyncTask extends AsyncTask<Void, Void, List<SimpleHotel>> {

        private WeakReference<HotelListActivity> hotelListActivityWeakReference;

        public GetSimpleHotelAsyncTask(HotelListActivity hotelListActivity) {
            hotelListActivityWeakReference = new WeakReference<>(hotelListActivity);
        }

        @Override
        protected List<SimpleHotel> doInBackground(Void... params) {
            HotelListActivity hotelListActivity = hotelListActivityWeakReference.get();
            if (hotelListActivity != null)
                return SimpleHotel.fromJsonReader(Utils.getJsonReader(hotelListActivity,
                        hotelListActivity.getString(R.string.what_hotel_list)));
            return null;
        }

        @Override
        protected void onPostExecute(List<SimpleHotel> simpleHotels) {
            super.onPostExecute(simpleHotels);
            if (simpleHotels != null) {
                HotelListActivity hotelListActivity = hotelListActivityWeakReference.get();
                if (hotelListActivity != null)
                    hotelListActivity.setSimpleHotels(simpleHotels);
            }

        }
    }
}
