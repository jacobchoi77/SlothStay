package com.moffcomm.slothstay.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.JsonReader;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.SimpleHotel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-14.
 */
public class Utils {

    public static JsonReader getJsonReader(Context context, final String what) {
        if (Constants.DATA_FROM_ASSETS) {
            try {
                InputStream inputStream = context.getAssets().open(what + ".json");
                return new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            } catch (IOException e) {
            }
        } else {

        }

        return null;
    }

    public static void reorder(List<SimpleHotel> simpleHotels) {
        int alone = -1;
        for (int i = 0; i < simpleHotels.size(); i++) {
            SimpleHotel simpleHotel = simpleHotels.get(i);
            if (simpleHotel.getRate() < Constants.RATE_BASE) {
                if (alone == -1) {
                    alone = i;
                } else {
                    SimpleHotel temp = simpleHotels.get(alone + 1);
                    simpleHotels.add(alone + 1, simpleHotel);
                    simpleHotels.remove(i);
                    simpleHotels.add(i, temp);
                    alone = -1;
                }
            }
        }
    }

    public static String getDateString(Date date, String format) {
        if (date == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Bitmap getMarkerBitmapFromView(Context context, String price) {
        View customMarkerView = ((LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_icon, null);
        ((TextView) customMarkerView.findViewById(R.id.priceTextView)).setText(price);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    public static int getDisplayContentHeight(Activity activity) {
        final WindowManager windowManager = activity.getWindowManager();
        final Point size = new Point();
        int screenHeight = 0, actionBarHeight = 0;
        if (activity.getActionBar() != null) {
            actionBarHeight = activity.getActionBar().getHeight();
        }
        int contentTop = ((ViewGroup) activity.findViewById(android.R.id.content)).getTop();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            windowManager.getDefaultDisplay().getSize(size);
            screenHeight = size.y;
        } else {
            Display d = windowManager.getDefaultDisplay();
            screenHeight = d.getHeight();
        }
        return screenHeight - contentTop - actionBarHeight;
    }

    public static Date getTodayCheckInDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }

    public static Date getTomorrowCheckOutDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return calendar.getTime();
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

}
