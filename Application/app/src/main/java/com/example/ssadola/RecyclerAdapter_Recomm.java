package com.example.ssadola;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerAdapter_Recomm extends RecyclerView.Adapter<RecyclerAdapter_Recomm.ViewHolderRecomm> {
    Context context;
    List<Item> items;
    private ArrayList<HashMap<String,String>> RecommList;
    ArrayList<HashMap<String, String>> arrayList;
    Bitmap[] bm;
    String tag_season;
    int item_layout;
    private static final String TAG_EMAIL="u_email";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_ADDR = "f_addr";
    private static final String TAG_NAME = "f_name";
    static String pub_ip = "http://15.165.95.187/";
    String u_email;

    public RecyclerAdapter_Recomm (Context context, ArrayList<HashMap<String,String>> List) {
        this.context = context;
        this.RecommList = List;
    }
    @NonNull
    @Override
    public ViewHolderRecomm onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recomm,null);
        return new ViewHolderRecomm(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolderRecomm holder, int position) {
        final HashMap<String,String> RecommItem = RecommList.get(position);
        holder.tv_course.setText(RecommItem.get("course"));
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int getItemCount() {
        return this.RecommList.size();
    }

    public class ViewHolderRecomm extends RecyclerView.ViewHolder {
        TextView tv_course;

        CardView cv;
        //ImageButton btn_delete;

        public ViewHolderRecomm(View v) {
            super(v);

            //tv_location = (TextView) v.findViewById(R.id.tv_location);
            tv_course = (TextView) v.findViewById(R.id.tv_recomm_course);
            cv = (CardView) v.findViewById(R.id.cardview_recomm);
            //tn_delete= v.findViewById(R.id.btn_delete);
        }
    }
}
