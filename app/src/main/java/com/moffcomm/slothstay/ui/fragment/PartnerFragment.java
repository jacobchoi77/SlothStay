package com.moffcomm.slothstay.ui.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.customtabs.CustomTabActivityHelper;
import com.moffcomm.slothstay.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerFragment extends Fragment implements View.OnClickListener {

    private View mContentView;
    private CustomTabActivityHelper customTabActivityHelper;

    public PartnerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_partner, container, false);
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContentView.findViewById(R.id.airRelativeLayout).setOnClickListener(this);
        mContentView.findViewById(R.id.carRelativeLayout).setOnClickListener(this);
        mContentView.findViewById(R.id.pensionRelativeLayout).setOnClickListener(this);
        mContentView.findViewById(R.id.packageRelativeLayout).setOnClickListener(this);
        mContentView.findViewById(R.id.ticketRelativeLayout).setOnClickListener(this);
        mContentView.findViewById(R.id.guideRelativeLayout).setOnClickListener(this);
        customTabActivityHelper = new CustomTabActivityHelper();
    }

    @Override
    public void onStart() {
        super.onStart();
        customTabActivityHelper.bindCustomTabsService(getActivity());
        customTabActivityHelper.mayLaunchUrl(Uri.parse(getString(R.string.url_air)), null, null);
        customTabActivityHelper.mayLaunchUrl(Uri.parse(getString(R.string.url_car)), null, null);
        customTabActivityHelper.mayLaunchUrl(Uri.parse(getString(R.string.url_pension)), null, null);
        customTabActivityHelper.mayLaunchUrl(Uri.parse(getString(R.string.url_package)), null, null);
        customTabActivityHelper.mayLaunchUrl(Uri.parse(getString(R.string.url_guide)), null, null);
    }

    @Override
    public void onStop() {
        customTabActivityHelper.unbindCustomTabsService(getActivity());
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.airRelativeLayout:
                Utils.goUrl(getActivity(), customTabActivityHelper, getString(R.string.url_air));
                break;
            case R.id.carRelativeLayout:
                Utils.goUrl(getActivity(), customTabActivityHelper, getString(R.string.url_car));
                break;
            case R.id.pensionRelativeLayout:
                Utils.goUrl(getActivity(), customTabActivityHelper, getString(R.string.url_pension));
                break;
            case R.id.packageRelativeLayout:
                Utils.goUrl(getActivity(), customTabActivityHelper, getString(R.string.url_package));
                break;
            case R.id.ticketRelativeLayout:
                Toast.makeText(getActivity(), R.string.service_preparing, Toast.LENGTH_SHORT).show();
                break;
            case R.id.guideRelativeLayout:
                Utils.goUrl(getActivity(), customTabActivityHelper, getString(R.string.url_guide));
                break;
        }

    }
}
