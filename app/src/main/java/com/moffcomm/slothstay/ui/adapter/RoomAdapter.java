package com.moffcomm.slothstay.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.Room;

import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-10.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private List<Room> rooms;
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Room room = rooms.get(position);
        viewHolder.roomNameTextView.setText(room.getName());
        viewHolder.priceTextView.setText(room.getPrice());
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView imageView;
        public TextView roomNameTextView;
        public TextView priceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            roomNameTextView = (TextView) itemView.findViewById(R.id.roomNameTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.priceTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public RoomAdapter(List<Room> rooms, Context context) {
        this.rooms = rooms;
        mContext = context;
    }


}
