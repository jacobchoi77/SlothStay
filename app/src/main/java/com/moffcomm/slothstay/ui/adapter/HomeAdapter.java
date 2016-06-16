package com.moffcomm.slothstay.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moffcomm.slothstay.Constants;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.HomeHotel;
import com.moffcomm.slothstay.ui.HotelActivity;

import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-10.
 */
public class HomeAdapter extends HeaderRecyclerViewAdapter {

    private List<HomeHotel> homeHotels;
    private Context mContext;
    private static final int TYPE_FULL = 1;
    private static final int TYPE_HALF = 2;

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameTextView;
        public TextView rateTextView;
        public TextView priceTextView;
        public TextView guestTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            rateTextView = (TextView) itemView.findViewById(R.id.rateTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.priceTextView);
            guestTextView = (TextView) itemView.findViewById(R.id.guestTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    HomeHotel hotel = homeHotels.get(position);
                    Intent intent = new Intent(mContext, HotelActivity.class);
                    intent.putExtra(HomeHotel.CONST_ID, hotel.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public HomeAdapter(List<HomeHotel> homeHotels, Context context) {
        this.homeHotels = homeHotels;
        mContext = context;
    }

    @Override
    public boolean useHeader() {
        return true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_home, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindHeaderView(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean useFooter() {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindFooterView(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType) {
            case TYPE_FULL:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
                break;
            case TYPE_HALF:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_small, parent, false);
                break;
        }
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        HomeHotel homeHotel = homeHotels.get(position);
        ((ViewHolder) holder).nameTextView.setText(homeHotel.getName());
        ((ViewHolder) holder).rateTextView.setText("" + homeHotel.getRate());
        ((ViewHolder) holder).priceTextView.setText(homeHotel.getPrice());
        Glide.with(mContext).load(homeHotel.getImageUrl()).into(((ViewHolder) holder).imageView);
    }

    @Override
    public int getBasicItemCount() {
        return homeHotels.size();
    }

    @Override
    public int getBasicItemType(int position) {
        final int newPosition = position - 1;
        HomeHotel homeHotel = homeHotels.get(newPosition);
        return homeHotel.getRate() < Constants.RATE_BASE ? TYPE_HALF : TYPE_FULL;
    }

}
