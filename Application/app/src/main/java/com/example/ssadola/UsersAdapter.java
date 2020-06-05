package com.example.ssadola;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {

    //private ArrayList<PersonalData> mList = null;
    private Activity context = null;
    ArrayList<HashMap<String,String>> mList;

    public UsersAdapter(Activity context,ArrayList<HashMap<String,String>>  list) {
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

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        HashMap<String,String> tmp =mList.get(position);
        /*viewholder.hotel.setText(tmp.getMember_hotel());
        viewholder.sight.setText(tmp.getMember_sight());
        viewholder.eat.setText(tmp.getMember_eat());
        viewholder.place.setText(tmp.getMember_place());
        viewholder.rating.setText(tmp.getMember_rating());
        viewholder.spec.setText(tmp.getMember_spec());*/
        viewholder.hotel.setText(tmp.get("hotel"));
        //Log.e("hotel.getText() : ", (String) viewholder.hotel.getText());
        viewholder.sight.setText(tmp.get("sight"));
        viewholder.eat.setText(tmp.get("eat"));
        viewholder.place.setText(tmp.get("place"));
        viewholder.rating.setText(tmp.get("rating"));
        viewholder.spec.setText(tmp.get("spec"));
    }

    @Override
    public int getItemCount() {
        Log.e("Adapter/mList.size() : ", String.valueOf(mList.size()));
        return mList.size();
    }
}