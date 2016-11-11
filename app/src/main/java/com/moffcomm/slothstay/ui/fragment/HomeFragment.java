package com.moffcomm.slothstay.ui.fragment;

import android.net.Uri;
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
import com.moffcomm.slothstay.customtabs.CustomTabActivityHelper;
import com.moffcomm.slothstay.model.SimpleHotel;
import com.moffcomm.slothstay.ui.adapter.HomeAdapter;
import com.moffcomm.slothstay.util.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-09.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private View mContentView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager gridLayoutManager;
    private List<SimpleHotel> simpleHotels = new ArrayList<>();
    private GetSimpleHotelAsyncTask mAsyncTask;
    private CustomTabActivityHelper customTabActivityHelper;

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

        customTabActivityHelper = new CustomTabActivityHelper();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAsyncTask != null && mAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            mAsyncTask.cancel(true);
        }
        mAsyncTask = new GetSimpleHotelAsyncTask(this);
        mAsyncTask.execute();

        customTabActivityHelper.bindCustomTabsService(getActivity());
        customTabActivityHelper.mayLaunchUrl(Uri.parse(getString(R.string.url_total)), null, null);
        customTabActivityHelper.mayLaunchUrl(Uri.parse(getString(R.string.url_agoda)), null, null);
        customTabActivityHelper.mayLaunchUrl(Uri.parse(getString(R.string.url_bookings)), null, null);
        customTabActivityHelper.mayLaunchUrl(Uri.parse(getString(R.string.url_hotels)), null, null);
    }

    @Override
    public void onStop() {
        customTabActivityHelper.unbindCustomTabsService(getActivity());
        super.onStop();
    }

    public void setSimpleHotels(List<SimpleHotel> simpleHotels) {
        this.simpleHotels.clear();
        this.simpleHotels.addAll(simpleHotels);
        mAdapter.notifyDataSetChanged();
        setupHeader();
    }

    private void setupHeader() {
        mContentView.findViewById(R.id.total).setOnClickListener(this);
        mContentView.findViewById(R.id.agoda).setOnClickListener(this);
        mContentView.findViewById(R.id.bookings).setOnClickListener(this);
        mContentView.findViewById(R.id.hotels).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.total:
                Utils.goUrl(getActivity(), customTabActivityHelper, getString(R.string.url_total));
                break;
            case R.id.agoda:
                Utils.goUrl(getActivity(), customTabActivityHelper, getString(R.string.url_agoda));
                break;
            case R.id.bookings:
                Utils.goUrl(getActivity(), customTabActivityHelper, getString(R.string.url_bookings));
                break;
            case R.id.hotels:
                Utils.goUrl(getActivity(), customTabActivityHelper, getString(R.string.url_hotels));
                break;
        }
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
