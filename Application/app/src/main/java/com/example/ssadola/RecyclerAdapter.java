package com.example.ssadola;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<Item> items;
    ArrayList<HashMap<String,String>> bookmarkList;
    Bitmap[] bm;
    private static final String TAG_EMAIL="u_email";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_ADDR = "address";
    private static final String TAG_TITLE = "title";
    private static final String TAG_COUNT = "count";
    static String pub_ip = "http://15.165.95.187/";
    String u_email;

    public RecyclerAdapter(Context context, ArrayList<HashMap<String,String>> bookmarkList,Bitmap[] bitmaps,String email) {
        this.context = context;
        this.bookmarkList = bookmarkList;
        this.bm = bitmaps;
        this.u_email = email;
        //this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview,null);
        return new ViewHolder(v);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //final Item item = items.get(position);
        //MyCardView cardView = holder.cardView;
        //cardView.setData(item);
        final HashMap<String,String> BookmarkItem = bookmarkList.get(position);
        holder.tv_title.setText(BookmarkItem.get(TAG_TITLE));
        holder.tv_location.setText(BookmarkItem.get(TAG_LOCATION));
        holder.tv_address.setText(BookmarkItem.get(TAG_ADDR));
        holder.imageView.setImageBitmap(bm[position]);
        //holder.tv_email.setText(BookmarkItem.get(TAG_EMAIL));
        //holder.tv_count.setText(BookmarkItem.get(TAG_COUNT));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(v.getContext(),ImageStudioActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("title",BookmarkItem.get(TAG_TITLE));
                move.putExtras(bundle);
                context.startActivity(move);
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String title = BookmarkItem.get(TAG_TITLE);
                String location = BookmarkItem.get(TAG_LOCATION);
                System.out.println(u_email +title + " "+ location);
                Bookmark_delete delete = new Bookmark_delete();
                delete.execute(u_email,title,location);

                //BookmarkItem.remove(position);
                removeItemFromView(position);

            }
        });
        /*cardView.setUserActionListener(new MyCardView.UserActionListener() {
            @Override
            public void onImageClicked() {
                Toast.makeText(context, "image", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextClicked() {
                Toast.makeText(context, BookmarkItem.get(TAG_TITLE), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return this.bookmarkList.size();
    }
    public void removeItemFromView(int position) {
        this.bookmarkList.remove(position);
        notifyItemRemoved(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_count;
        TextView tv_date;
        TextView tv_address;
        TextView tv_location;
        TextView tv_email;
        CardView cv;
        ImageView imageView;
        ImageButton btn_delete;

        public ViewHolder(View v) {
            super(v);

            tv_location = (TextView) v.findViewById(R.id.tv_location);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            //tv_count = (TextView) v.findViewById(R.id.tv_count);
            tv_address = (TextView) v.findViewById(R.id.tv_address);
            //tv_email = v.findViewById(R.id.tv_writer);
            cv = (CardView) v.findViewById(R.id.cardview);
            imageView = v.findViewById(R.id.iv_bookmark);
            btn_delete = v.findViewById(R.id.btn_delete);
        }
    }

     /*   MyCardView cardView;


        public ViewHolder(View card) {
            super(card);
            cardView = (MyCardView)card;
        }*/

    class Bookmark_delete extends AsyncTask<String, Void, String> {

        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //loading.dismiss();
            System.out.println("Result : " + result);
            if(!result.equals("")){
                Toast.makeText(context.getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            }else{
                Log.e("Bookmark-Adapter","result is null");
            }
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                //u_email,i_title,i_scene,i_location,i_address,mImageUrl
                String u_email = (String) params[0];
                String title = (String) params[1];
                String location = params[2];


                String link = pub_ip+"Bookmark_delete.php";
                //String data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
                //data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");
                String data = "u_email="+u_email+"&title="+ URLEncoder.encode(title,"UTF-8")
                        + "&location="+URLEncoder.encode(location,"UTF-8");

                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();
                int responseStatusCode = conn.getResponseCode();
                Log.d("Bookmark_insert", "response code - " + responseStatusCode);
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream =conn.getInputStream();
                }
                else{
                    inputStream = conn.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                return sb.toString();
            } catch (Exception e) {
                return e.toString();
            }
        }
    }
}