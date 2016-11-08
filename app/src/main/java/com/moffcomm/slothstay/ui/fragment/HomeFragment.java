package com.moffcomm.slothstay.ui.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.SimpleHotel;
import com.moffcomm.slothstay.ui.adapter.HomeAdapter;
import com.moffcomm.slothstay.util.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-09.
 */
public class HomeFragment extends Fragment {

    private View mContentView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager gridLayoutManager;
    private int MAX_MAIN_BUTTON_LAYOUT_HEIGHT;
    private int MIN_MAIN_BUTTON_LAYOUT_HEIGHT;
    private int MAX_MAIN_BUTTON_IMAGE_HEIGHT;
    private int MIN_MAIN_BUTTON_IMAGE_HEIGHT;
    private int currentMainButtonLayoutHeight;
    private int currentMainButtonImageHeight;
    private View mMainButtonLayout;
    private List<SimpleHotel> simpleHotels = new ArrayList<>();
    private GetSimpleHotelAsyncTask mAsyncTask;
    private ImageView airImageView;
    private ImageView carImageView;
    private ImageView ticketImageView;
    private ImageView pensionImageView;
    private ImageView guideImageView;
    private ImageView packageTripImageView;

    private int previousDy = -1;
    private ViewGroup.LayoutParams buttonlayoutParams;
    private ViewGroup.LayoutParams airParams;
    private ViewGroup.LayoutParams carParams;
    private ViewGroup.LayoutParams ticketParams;
    private ViewGroup.LayoutParams pensionParams;
    private ViewGroup.LayoutParams guideParams;
    private ViewGroup.LayoutParams packageTripParams;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_home, container, false);
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(getContext(), Constants.GRID_SPAN_COUNT,
                GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                final int newPosition = position - 1;
                final int size = simpleHotels.size();
                if (newPosition >= 0 && newPosition < size)
                    return simpleHotels.get(newPosition).getRate() < Constants.RATE_BASE ? 1 : 2;
                else
                    return 2;
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new HomeAdapter(simpleHotels, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mMainButtonLayout = mContentView.findViewById(R.id.mainButtonLayout);
        airImageView = (ImageView) mContentView.findViewById(R.id.airImageView);
        carImageView = (ImageView) mContentView.findViewById(R.id.carImageView);
        ticketImageView = (ImageView) mContentView.findViewById(R.id.ticketImageView);
        pensionImageView = (ImageView) mContentView.findViewById(R.id.pensionImageView);
        guideImageView = (ImageView) mContentView.findViewById(R.id.guideImageView);
        packageTripImageView = (ImageView) mContentView.findViewById(R.id.packageTripImageView);
        mMainButtonLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                if (MAX_MAIN_BUTTON_LAYOUT_HEIGHT == 0) {
                    MAX_MAIN_BUTTON_LAYOUT_HEIGHT = mMainButtonLayout.getMeasuredHeight();
                    MIN_MAIN_BUTTON_LAYOUT_HEIGHT = MAX_MAIN_BUTTON_LAYOUT_HEIGHT -
                            getResources().getDimensionPixelOffset(R.dimen.main_button_shrink);
                    MAX_MAIN_BUTTON_IMAGE_HEIGHT = airImageView.getMeasuredHeight();
                    MIN_MAIN_BUTTON_IMAGE_HEIGHT = MAX_MAIN_BUTTON_IMAGE_HEIGHT -
                            getResources().getDimensionPixelOffset(R.dimen.main_button_shrink);
                    currentMainButtonLayoutHeight = MAX_MAIN_BUTTON_LAYOUT_HEIGHT;
                    currentMainButtonImageHeight = MAX_MAIN_BUTTON_IMAGE_HEIGHT;
                    buttonlayoutParams = mMainButtonLayout.getLayoutParams();
                    airParams = airImageView.getLayoutParams();
                    carParams = carImageView.getLayoutParams();
                    ticketParams = ticketImageView.getLayoutParams();
                    pensionParams = pensionImageView.getLayoutParams();
                    guideParams = guideImageView.getLayoutParams();
                    packageTripParams = packageTripImageView.getLayoutParams();
                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (previousDy == dy)
                                return;
                            previousDy = dy;
                            currentMainButtonLayoutHeight -= dy;
                            currentMainButtonImageHeight -= dy;
                            if (currentMainButtonLayoutHeight < MIN_MAIN_BUTTON_LAYOUT_HEIGHT) {
                                currentMainButtonLayoutHeight = MIN_MAIN_BUTTON_LAYOUT_HEIGHT;
                                currentMainButtonImageHeight = MIN_MAIN_BUTTON_IMAGE_HEIGHT;
                            } else if (currentMainButtonLayoutHeight > MAX_MAIN_BUTTON_LAYOUT_HEIGHT) {
                                currentMainButtonLayoutHeight = MAX_MAIN_BUTTON_LAYOUT_HEIGHT;
                                currentMainButtonImageHeight = MAX_MAIN_BUTTON_IMAGE_HEIGHT;
                            }
                            buttonlayoutParams.height = currentMainButtonLayoutHeight;
                            airParams.height = currentMainButtonImageHeight;
                            airParams.width = currentMainButtonImageHeight;
                            carParams.height = currentMainButtonImageHeight;
                            carParams.width = currentMainButtonImageHeight;
                            ticketParams.height = currentMainButtonImageHeight;
                            ticketParams.width = currentMainButtonImageHeight;
                            pensionParams.height = currentMainButtonImageHeight;
                            pensionParams.width = currentMainButtonImageHeight;
                            guideParams.height = currentMainButtonImageHeight;
                            guideParams.width = currentMainButtonImageHeight;
                            packageTripParams.height = currentMainButtonImageHeight;
                            packageTripParams.width = currentMainButtonImageHeight;

                            airImageView.forceLayout();
                            carImageView.forceLayout();
                            ticketImageView.requestLayout();
                            pensionImageView.forceLayout();
                            guideImageView.forceLayout();
                            packageTripImageView.requestLayout();
                            mMainButtonLayout.requestLayout();
                        }
                    });

                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                    mMainButtonLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    mMainButtonLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
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
    public void onDestroy() {
        super.onDestroy();
        mRecyclerView.clearOnScrollListeners();
    }

    public void setSimpleHotels(List<SimpleHotel> simpleHotels) {
        this.simpleHotels.clear();
        this.simpleHotels.addAll(simpleHotels);
        mAdapter.notifyDataSetChanged();
    }

    private static class GetSimpleHotelAsyncTask extends AsyncTask<Void, Void, List<SimpleHotel>> {

        private WeakReference<HomeFragment> homeFragmentWeakReference;

        public GetSimpleHotelAsyncTask(HomeFragment homeFragment) {
            homeFragmentWeakReference = new WeakReference<>(homeFragment);
        }

        @Override
        protected List<SimpleHotel> doInBackground(Void... params) {
            HomeFragment homeFragment = homeFragmentWeakReference.get();
            if (homeFragment != null)
                return SimpleHotel.fromJsonReader(Utils.getJsonReader(homeFragment.getContext(),
                        homeFragment.getString(R.string.what_home)));
            return null;
        }

        @Override
        protected void onPostExecute(List<SimpleHotel> simpleHotels) {
            super.onPostExecute(simpleHotels);
            if (simpleHotels != null) {
                Utils.reorder(simpleHotels);
                HomeFragment homeFragment = homeFragmentWeakReference.get();
                if (homeFragment != null)
                    homeFragment.setSimpleHotels(simpleHotels);
            }

        }
    }


}
