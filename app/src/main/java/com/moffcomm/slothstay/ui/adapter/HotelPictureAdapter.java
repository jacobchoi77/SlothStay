package com.moffcomm.slothstay.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.Picture;

import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-16.
 */
public class HotelPictureAdapter extends PagerAdapter {

    private List<Picture> pictures;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public HotelPictureAdapter(Context context, List<Picture> pictures) {
        mContext = context;
        this.pictures = pictures;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_hotel_picture, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Glide.with(mContext).load(pictures.get(position).getImageUrl()).into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}