package com.moffcomm.slothstay.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.SlothStayApplication;
import com.moffcomm.slothstay.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class MileageFragment extends Fragment implements View.OnClickListener {

    private View mContentView;
    private User user;

    public MileageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_mileage, container, false);
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = ((SlothStayApplication) getActivity().getApplication()).getUser();
        if (user == null)
            Toast.makeText(getActivity(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
        mContentView.findViewById(R.id.saveTextView).setOnClickListener(this);
        mContentView.findViewById(R.id.useTextView).setOnClickListener(this);
        mContentView.findViewById(R.id.guideTextView).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(), R.string.service_preparing, Toast.LENGTH_SHORT).show();
    }
}
