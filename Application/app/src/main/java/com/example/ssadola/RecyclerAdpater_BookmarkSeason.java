package com.example.ssadola;



import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

class RecyclerAdapter_BookmarkSeason extends RecyclerView.Adapter<RecyclerAdapter_BookmarkSeason.ViewHolderBookmarkSeason> {
    Context context;
    List<Item> items;
    private ArrayList<HashMap<String,String>> festivalList;
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

    public RecyclerAdapter_BookmarkSeason(Context context, ArrayList<HashMap<String,String>> List,Bitmap[] bitmaps,int item_layout, String season) {
        this.context = context;
        this.festivalList = List;
        this.bm = bitmaps;
        this.item_layout = item_layout;
        this.tag_season = season;
    }
    public RecyclerAdapter_BookmarkSeason (Context context, ArrayList<HashMap<String,String>> List,Bitmap[] bitmaps,int item_layout) {
        this.context = context;
        this.festivalList = List;
        this.bm = bitmaps;
        this.item_layout = item_layout;
    }
    public RecyclerAdapter_BookmarkSeason(Context context, ArrayList<HashMap<String,String>> List,int item_layout) {
        this.context = context;
        this.festivalList = List;
        //this.bm = bitmaps;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolderBookmarkSeason onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_season,null);
        return new ViewHolderBookmarkSeason(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolderBookmarkSeason holder, final int position) {
//final Item item = items.get(position);
        //MyCardView cardView = holder.cardView;
        //cardView.setData(item);
        final HashMap<String,String> festivalItem = festivalList.get(position);
        holder.tv_name.setText(festivalItem.get(TAG_NAME));
        //holder.tv_location.setText(festivalItem .get(TAG_LOCATION));
        holder.tv_address.setText(festivalItem.get(TAG_ADDR));
        holder.imageView.setImageBitmap(bm[position]);
        arrayList = GetLoginData();
        holder.btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    HashMap<String,String> hashMap = arrayList.get(0);
                    String u_email = hashMap.get("u_email");
                    String name = festivalItem.get(TAG_NAME);
                    String addr = festivalItem.get(TAG_ADDR);
                    System.out.println(u_email +name + " "+ addr);
                    Bookmark_delete delete = new Bookmark_delete();
                    delete.execute(u_email,name,addr);

                    removeItemFromView(position);
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void removeItemFromView(int position) {
        this.festivalList.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return this.festivalList.size();
    }

    public class ViewHolderBookmarkSeason extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_count;
        TextView tv_date;
        TextView tv_address;
        TextView tv_location;
        TextView tv_email;
        CardView cv;
        ImageView imageView;
        ImageButton btn_delete;

        public ViewHolderBookmarkSeason(View v) {
            super(v);

            //tv_location = (TextView) v.findViewById(R.id.tv_location);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            //tv_count = (TextView) v.findViewById(R.id.tv_count);
            tv_address = (TextView) v.findViewById(R.id.tv_address);
            //tv_email = v.findViewById(R.id.tv_writer);
            cv = (CardView) v.findViewById(R.id.cardview_season);
            imageView = v.findViewById(R.id.iv_festival);
            btn_delete= v.findViewById(R.id.btn_delete_s);
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
            if(result!=null){
                Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
            }else{
                Log.e("ImageStudioFragment","result is null");
            }
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                //u_email,i_title,i_scene,i_location,i_address,mImageUrl
                String u_email = (String) params[0];
                String name = (String) params[1];
                String addr = params[2];
                //String season = tag_season;


                String link = pub_ip+"Bookmark_deleteSeason.php";
                //String data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
                //data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");
                String data = "u_email="+u_email+"&f_name="+URLEncoder.encode(name,"UTF-8")
                        + "&f_addr="+URLEncoder.encode(addr,"UTF-8");


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
    public ArrayList<HashMap<String, String>> GetLoginData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("UserInfo", "EMPTY");
        if(json.equals("EMPTY")){
            //Toast.makeText(BookmarkActivity.this,"로그인 먼저 해주세요",Toast.LENGTH_LONG).show();
            return null;
        }
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}