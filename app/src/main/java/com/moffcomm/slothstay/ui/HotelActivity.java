package com.moffcomm.slothstay.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.Hotel;
import com.moffcomm.slothstay.util.Utils;

import java.lang.ref.WeakReference;

public class HotelActivity extends AppCompatActivity {

    private Hotel hotel;
    private int hotelId;
    private GetHotelAsyncTask mAsyncTask;

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
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
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
