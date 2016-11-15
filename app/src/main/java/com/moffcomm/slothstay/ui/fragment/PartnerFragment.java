package com.moffcomm.slothstay.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.WebViewInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerFragment extends Fragment implements View.OnClickListener {

    private View mContentView;
    private WebViewInterface webViewInterface;

    public PartnerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_partner, container, false);
        webViewInterface = (WebViewInterface) getActivity();
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
    }

    @Override
    public void onStart() {
        super.onStart();
        webViewInterface.mayShowWebView(getString(R.string.url_air));
        webViewInterface.mayShowWebView(getString(R.string.url_car));
        webViewInterface.mayShowWebView(getString(R.string.url_pension));
        webViewInterface.mayShowWebView(getString(R.string.url_package));
        webViewInterface.mayShowWebView(getString(R.string.url_guide));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.airRelativeLayout:
                webViewInterface.showWebView(getString(R.string.url_air));
                break;
            case R.id.carRelativeLayout:
                webViewInterface.showWebView(getString(R.string.url_car));
                break;
            case R.id.pensionRelativeLayout:
                webViewInterface.showWebView(getString(R.string.url_pension));
                break;
            case R.id.packageRelativeLayout:
                webViewInterface.showWebView(getString(R.string.url_package));
                break;
            case R.id.ticketRelativeLayout:
                Toast.makeText(getActivity(), R.string.service_preparing, Toast.LENGTH_SHORT).show();
                break;
            case R.id.guideRelativeLayout:
                webViewInterface.showWebView(getString(R.string.url_guide));
                break;
        }

    }
}
