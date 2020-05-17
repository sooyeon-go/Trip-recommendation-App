package com.example.ssadola;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<Item> items;
    ArrayList<HashMap<String,String>> bookmarkList; //공지사항 정보 담겨있음
    private static final String TAG_EMAIL="u_email";
    private static final String TAG_NAME = "u_name";
    private static final String TAG_THEME = "theme";
    private static final String TAG_ADDR = "addr";
    private static final String TAG_WOKR = "work_nm";
    private static final String TAG_COUNT = "count";


    public RecyclerAdapter(Context context, ArrayList<HashMap<String,String>> bookmarkList) {
        this.context = context;
        this.bookmarkList = bookmarkList;
        //this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview,null);
        return new ViewHolder(v);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*final Item item = items.get(position);
        MyCardView cardView = holder.cardView;
        cardView.setData(item);*/
        HashMap<String,String> BookmarkItem = bookmarkList.get(position);
        holder.tv_title.setText(BookmarkItem.get(TAG_WOKR));
        holder.tv_theme.setText(BookmarkItem.get(TAG_THEME));
        holder.tv_address.setText(BookmarkItem.get(TAG_ADDR));
        holder.tv_email.setText(BookmarkItem.get(TAG_EMAIL));
        holder.tv_count.setText(BookmarkItem.get(TAG_COUNT));

        /*cardView.setUserActionListener(new MyCardView.UserActionListener() {
            @Override
            public void onImageClicked() {
                Toast.makeText(context, "image", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextClicked() {
                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return this.bookmarkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_count;
        TextView tv_date;
        TextView tv_address;
        TextView tv_theme;
        TextView tv_email;
        CardView cv;

        public ViewHolder(View v) {
            super(v);
            tv_theme = (TextView) v.findViewById(R.id.tv_theme);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_count = (TextView) v.findViewById(R.id.tv_count);
            tv_address = (TextView) v.findViewById(R.id.tv_address);
            tv_email = v.findViewById(R.id.tv_writer);
            cv = (CardView) v.findViewById(R.id.cardview);
        }
    }

        /*MyCardView cardView;


        public ViewHolder(View card) {
            super(card);
            cardView = (MyCardView)card;
        }*/
}