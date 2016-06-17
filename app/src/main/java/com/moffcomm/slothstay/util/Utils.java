package com.moffcomm.slothstay.util;

import android.content.Context;
import android.util.JsonReader;

import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.model.HomeHotel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
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

    public static void reorder(List<HomeHotel> homeHotels) {
        int alone = -1;
        for (int i = 0; i < homeHotels.size(); i++) {
            HomeHotel homeHotel = homeHotels.get(i);
            if (homeHotel.getRate() < Constants.RATE_BASE) {
                if (alone == -1) {
                    alone = i;
                } else {
                    HomeHotel temp = homeHotels.get(alone + 1);
                    homeHotels.add(alone + 1, homeHotel);
                    homeHotels.remove(i);
                    homeHotels.add(i, temp);
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

}
