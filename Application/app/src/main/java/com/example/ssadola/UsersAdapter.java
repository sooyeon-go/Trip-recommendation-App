package com.example.ssadola;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {

    private ArrayList<PersonalData> mList = null;
    private Activity context = null;


    public UsersAdapter(Activity context, ArrayList<PersonalData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView hotel;
        protected TextView sight;
        protected TextView eat;
        protected TextView place;
        protected TextView rating;
        protected TextView spec;

        public CustomViewHolder(View view) {
            super(view);
            this.hotel = (TextView) view.findViewById(R.id.hotel);
            this.sight = (TextView) view.findViewById(R.id.sight);
            this.eat = (TextView) view.findViewById(R.id.eat);
            this.place = (TextView) view.findViewById(R.id.place);
            this.rating = (TextView) view.findViewById(R.id.rating);
            this.spec = (TextView) view.findViewById(R.id.spec);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.hotel.setText(mList.get(position).getMember_hotel());
        viewholder.sight.setText(mList.get(position).getMember_sight());
        viewholder.eat.setText(mList.get(position).getMember_eat());
        viewholder.place.setText(mList.get(position).getMember_place());
        viewholder.rating.setText(mList.get(position).getMember_rating());
        viewholder.spec.setText(mList.get(position).getMember_spec());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}