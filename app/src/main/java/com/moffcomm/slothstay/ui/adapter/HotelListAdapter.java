package com.moffcomm.slothstay.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.SimpleHotel;
import com.moffcomm.slothstay.ui.HotelActivity;

import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-10.
 */
public class HotelListAdapter extends RecyclerView.Adapter {

    private List<SimpleHotel> simpleHotels;
    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleHotel simpleHotel = simpleHotels.get(position);
        ((ViewHolder) holder).nameTextView.setText(simpleHotel.getName());
        ((ViewHolder) holder).rateTextView.setText("" + simpleHotel.getRate());
        ((ViewHolder) holder).priceTextView.setText(simpleHotel.getPrice());
        Glide.with(mContext).load(simpleHotel.getImageUrl()).into(((ViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return simpleHotels.size();
    }

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
                    SimpleHotel hotel = simpleHotels.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, HotelActivity.class);
                    intent.putExtra(SimpleHotel.CONST_ID, hotel.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public HotelListAdapter(List<SimpleHotel> simpleHotels, Context context) {
        this.simpleHotels = simpleHotels;
        mContext = context;
    }

}
