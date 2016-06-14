package com.moffcomm.slothstay.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moffcomm.slothstay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyReservationFragment extends Fragment {


    public MyReservationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_reservation, container, false);
    }

}
