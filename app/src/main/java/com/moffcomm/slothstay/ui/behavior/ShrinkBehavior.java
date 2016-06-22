package com.moffcomm.slothstay.ui.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.MapView;

public class ShrinkBehavior extends CoordinatorLayout.Behavior<MapView> {

    public ShrinkBehavior() {
    }

    public ShrinkBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, MapView child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, MapView child, View dependency) {
        int y = ((RecyclerView) dependency).getScrollY();
        Log.v("jacob", "jacob: " + y);
        return false;
    }

}
