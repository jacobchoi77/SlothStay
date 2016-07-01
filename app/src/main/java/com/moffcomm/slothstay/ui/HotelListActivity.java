package com.moffcomm.slothstay.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

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
                finish();
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
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    fab.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.fab_list);
                } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    fab.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.fab_map);
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
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
