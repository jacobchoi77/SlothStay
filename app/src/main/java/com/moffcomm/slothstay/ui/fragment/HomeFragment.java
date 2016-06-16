package com.moffcomm.slothstay.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.HomeHotel;
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
    private int currentMainButtonLayoutHeight;
    private View mMainButtonLayout;
    private List<HomeHotel> homeHotels = new ArrayList<>();
    private GetHomeHotelAsyncTask mAsyncTask;

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
                final int size = homeHotels.size();
                if (newPosition >= 0 && newPosition < size)
                    return homeHotels.get(newPosition).getRate() < Constants.RATE_BASE ? 1 : 2;
                else
                    return 2;
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new HomeAdapter(homeHotels, getContext());
        mRecyclerView.setAdapter(mAdapter);
//        mMainButtonLayout = mContentView.findViewById(R.id.mainButtonLayout);
//        mMainButtonLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (MAX_MAIN_BUTTON_LAYOUT_HEIGHT == 0) {
//                    MAX_MAIN_BUTTON_LAYOUT_HEIGHT = mMainButtonLayout.getMeasuredHeight();
//                    MIN_MAIN_BUTTON_LAYOUT_HEIGHT = MAX_MAIN_BUTTON_LAYOUT_HEIGHT - Constants.MAIN_BUTTON_LAYOUT_DIFF;
//                    currentMainButtonLayoutHeight = MAX_MAIN_BUTTON_LAYOUT_HEIGHT;
//                    mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//                        @Override
//                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                            super.onScrolled(recyclerView, dx, dy);
//                            currentMainButtonLayoutHeight -= dy;
//                            if (currentMainButtonLayoutHeight < MIN_MAIN_BUTTON_LAYOUT_HEIGHT)
//                                currentMainButtonLayoutHeight = MIN_MAIN_BUTTON_LAYOUT_HEIGHT;
//                            else if (currentMainButtonLayoutHeight > MAX_MAIN_BUTTON_LAYOUT_HEIGHT)
//                                currentMainButtonLayoutHeight = MAX_MAIN_BUTTON_LAYOUT_HEIGHT;
//                            ViewGroup.LayoutParams layoutParams = mMainButtonLayout.getLayoutParams();
//                            layoutParams.height = currentMainButtonLayoutHeight;
//                            mMainButtonLayout.setLayoutParams(layoutParams);
//                        }
//                    });
//                }
//            }
//        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAsyncTask != null && mAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            mAsyncTask.cancel(true);
        }
        mAsyncTask = new GetHomeHotelAsyncTask(this);
        mAsyncTask.execute();
    }

    public void setHomeHotels(List<HomeHotel> homeHotels) {
        this.homeHotels.clear();
        this.homeHotels.addAll(homeHotels);
        mAdapter.notifyDataSetChanged();
    }

    private static class GetHomeHotelAsyncTask extends AsyncTask<Void, Void, List<HomeHotel>> {

        private WeakReference<HomeFragment> homeFragmentWeakReference;

        public GetHomeHotelAsyncTask(HomeFragment homeFragment) {
            homeFragmentWeakReference = new WeakReference<>(homeFragment);
        }

        @Override
        protected List<HomeHotel> doInBackground(Void... params) {
            HomeFragment homeFragment = homeFragmentWeakReference.get();
            if (homeFragment != null)
                return HomeHotel.fromJsonReader(Utils.getJsonReader(homeFragment.getContext(),
                        homeFragment.getString(R.string.what_home)));
            return null;
        }

        @Override
        protected void onPostExecute(List<HomeHotel> homeHotels) {
            super.onPostExecute(homeHotels);
            if (homeHotels != null) {
                Utils.reorder(homeHotels);
                HomeFragment homeFragment = homeFragmentWeakReference.get();
                if (homeFragment != null)
                    homeFragment.setHomeHotels(homeHotels);
            }

        }
    }


}