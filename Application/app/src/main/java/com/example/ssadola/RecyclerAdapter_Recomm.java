package com.example.ssadola;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
    public void onBindViewHolder(final ViewHolderRecomm holder, final int position) {
        final HashMap<String,String> RecommItem = RecommList.get(position);
        holder.tv_course.setText(RecommItem.get("course"));

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String course = RecommItem.get("course");
                System.out.println("delete course : "+course);
                Bookmark_delete delete = new Bookmark_delete();
                delete.execute(u_email,course);

                //BookmarkItem.remove(position);
                removeItemFromView(position);

            }
        });
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int getItemCount() {
        return this.RecommList.size();
    }
    public void removeItemFromView(int position) {
        this.RecommList.remove(position);
        notifyItemRemoved(position);
    }
    public class ViewHolderRecomm extends RecyclerView.ViewHolder {
        TextView tv_course;

        CardView cv;
        ImageButton btn_delete;

        public ViewHolderRecomm(View v) {
            super(v);

            //tv_location = (TextView) v.findViewById(R.id.tv_location);
            tv_course = (TextView) v.findViewById(R.id.tv_recomm_course);
            cv = (CardView) v.findViewById(R.id.cardview_recomm);
            btn_delete= v.findViewById(R.id.btn_delete_re);
        }
    }

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
                String course= (String) params[1];



                String link = pub_ip+"Bookmark_deleteRecomm.php";
                //String data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
                //data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");
                String data = "u_email="+u_email+"&course="+ URLEncoder.encode(course,"UTF-8");

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
