package com.moffcomm.slothstay.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.SlothStayApplication;
import com.moffcomm.slothstay.model.Reservation;
import com.moffcomm.slothstay.ui.MainActivity;
import com.moffcomm.slothstay.ui.adapter.ReservationListAdapter;
import com.moffcomm.slothstay.util.Utils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyReservationFragment extends Fragment {

    private View mContentView;
    private RecyclerView mRecyclerView;
    private CustomGridLayoutManager linearLayoutManager;
    private List<Reservation> reservationList;
    private ReservationListAdapter mAdapter;

    public MyReservationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_my_reservation, container, false);
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reservationList = ((SlothStayApplication) getActivity().getApplication()).getReservationList();
        if (reservationList == null) {
            reservationList = Reservation.fromJsonReader(
                    Utils.getJsonReader(getActivity(), getString(R.string.what_reservation)));
        }
        mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.recyclerView);
        linearLayoutManager = new CustomGridLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ReservationListAdapter(reservationList, (MainActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void enableScroll() {
        linearLayoutManager.setScrollEnabled(true);
    }

    public void disableScroll() {
        linearLayoutManager.setScrollEnabled(false);
    }

    class CustomGridLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public CustomGridLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            return isScrollEnabled && super.canScrollVertically();
        }
    }

}
