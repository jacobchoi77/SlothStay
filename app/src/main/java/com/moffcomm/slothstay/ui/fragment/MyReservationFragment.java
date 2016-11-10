package com.moffcomm.slothstay.ui.fragment;


import android.content.Context;
import android.content.Intent;
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
import com.moffcomm.slothstay.model.Book;
import com.moffcomm.slothstay.model.Reservation;
import com.moffcomm.slothstay.ui.MainActivity;
import com.moffcomm.slothstay.ui.adapter.ReservationListAdapter;
import com.moffcomm.slothstay.util.Utils;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyReservationFragment extends Fragment {

    private View mContentView;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private CustomGridLayoutManager linearLayoutManager;
    private List<Reservation> reservationList;
    private ReservationListAdapter mAdapter;
    public static final int REQUEST_CODE_CHECK = 11;

    public MyReservationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_my_reservation, container, false);
        return mContentView;
    }

    public void refresh() {
        ((MainActivity) getActivity()).showToolBar();
        reservationList = ((SlothStayApplication) getActivity().getApplication()).getReservationList();
        mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.recyclerView);
        mEmptyView = mContentView.findViewById(R.id.emptyView);
        if (reservationList == null || reservationList.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            linearLayoutManager = new CustomGridLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new ReservationListAdapter(reservationList, (MainActivity) getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
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
