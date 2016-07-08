package com.moffcomm.slothstay.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.Reservation;
import com.moffcomm.slothstay.ui.MainActivity;
import com.moffcomm.slothstay.ui.fragment.MyReservationFragment;
import com.moffcomm.slothstay.util.Utils;

import java.util.List;

/**
 * Created by jacobsFactory on 2016-06-10.
 */
public class ReservationListAdapter extends RecyclerView.Adapter {

    private List<Reservation> reservationList;
    private MainActivity mainActivity;
    private MyReservationFragment myReservationFragment;

    public ReservationListAdapter(List<Reservation> reservationList, MainActivity mainActivity) {
        this.reservationList = reservationList;
        this.mainActivity = mainActivity;
        myReservationFragment = mainActivity.getMyReservationFragment();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        ((ViewHolder) holder).topHotelNameTextView.setText(reservation.getHotelName());
        Glide.with(mainActivity).load(reservation.getImageUrl()).into(((ViewHolder) holder).imageView);
        ((ViewHolder) holder).hotelNameTextView.setText(reservation.getHotelName());
        ((ViewHolder) holder).topCheckInTextView.setText(Utils.getDateString(reservation.getCheckInDate(),
                mainActivity.getString(R.string.hotel_date_format)));
        ((ViewHolder) holder).checkInTextView.setText(Utils.getDateString(reservation.getCheckInDate(),
                mainActivity.getString(R.string.hotel_date_format)));
        ((ViewHolder) holder).checkOutTextView.setText(Utils.getDateString(reservation.getCheckOutDate(),
                mainActivity.getString(R.string.hotel_date_format)));
        ((ViewHolder) holder).guestTextView.setText("" + reservation.getGuestCount());
        ((ViewHolder) holder).hotelAddressTextView.setText(reservation.getHotelAddress());
        ((ViewHolder) holder).hotelPhoneTextView.setText(reservation.getHotelPhone());
        ((ViewHolder) holder).roomTypeTextView.setText(reservation.getRoomName());
        ((ViewHolder) holder).itineraryTextView.setText(reservation.getItinerary());
        ((ViewHolder) holder).doExpandLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewHolder) holder).isExpanded = !((ViewHolder) holder).isExpanded;
                if (((ViewHolder) holder).isExpanded) {
                    Utils.expand(((ViewHolder) holder).expandLinearLayout);
                    Utils.expand(((ViewHolder) holder).topExpandLinearLayout);
                    ((ViewHolder) holder).hotelNameTextView.setVisibility(View.GONE);
                    mainActivity.hideToolBar();
                    myReservationFragment.disableScroll();
                    ViewGroup.LayoutParams layoutParams = ((ViewHolder) holder).scrollView.getLayoutParams();
                    layoutParams.height = Utils.getDisplayContentHeight(mainActivity) - 430;
                    ((ViewHolder) holder).scrollView.setLayoutParams(layoutParams);
                } else {
                    Utils.collapse(((ViewHolder) holder).expandLinearLayout);
                    Utils.collapse(((ViewHolder) holder).topExpandLinearLayout);
                    ((ViewHolder) holder).hotelNameTextView.setVisibility(View.VISIBLE);
                    mainActivity.showToolBar();
                    myReservationFragment.enableScroll();
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ((ViewHolder) holder).scrollView.setLayoutParams(layoutParams);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView topHotelNameTextView;
        public ImageView starImageView;
        public ImageView imageView;
        public TextView hotelNameTextView;
        public TextView topCheckInTextView;
        public ImageView folderImageView;
        public TextView checkInTextView;
        public TextView checkOutTextView;
        public TextView guestTextView;
        public TextView hotelAddressTextView;
        public TextView hotelPhoneTextView;
        public TextView roomTypeTextView;
        public TextView itineraryTextView;
        public View expandLinearLayout;
        public View topExpandLinearLayout;
        public View doExpandLinearLayout;
        public boolean isExpanded = false;
        public ScrollView scrollView;

        public ViewHolder(View itemView) {
            super(itemView);
            topHotelNameTextView = (TextView) itemView.findViewById(R.id.topHotelNameTextView);
            starImageView = (ImageView) itemView.findViewById(R.id.starImageView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            hotelNameTextView = (TextView) itemView.findViewById(R.id.hotelNameTextView);
            topCheckInTextView = (TextView) itemView.findViewById(R.id.topCheckInTextView);
            folderImageView = (ImageView) itemView.findViewById(R.id.folderImageView);
            checkInTextView = (TextView) itemView.findViewById(R.id.checkInTextView);
            checkOutTextView = (TextView) itemView.findViewById(R.id.checkOutTextView);
            guestTextView = (TextView) itemView.findViewById(R.id.guestTextView);
            hotelAddressTextView = (TextView) itemView.findViewById(R.id.hotelAddressTextView);
            hotelPhoneTextView = (TextView) itemView.findViewById(R.id.hotelPhoneTextView);
            roomTypeTextView = (TextView) itemView.findViewById(R.id.roomTypeTextView);
            itineraryTextView = (TextView) itemView.findViewById(R.id.itineraryTextView);
            expandLinearLayout = itemView.findViewById(R.id.expandLinearLayout);
            topExpandLinearLayout = itemView.findViewById(R.id.topExpandLinearLayout);
            doExpandLinearLayout = itemView.findViewById(R.id.doExpandLinearLayout);
            scrollView = (ScrollView) itemView.findViewById(R.id.scrollView);
        }
    }


}
