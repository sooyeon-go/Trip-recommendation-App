package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpringActivity extends AppCompatActivity {

    private RecyclerView rv;
    static String pub_ip = "http://15.165.95.187/";
    StringBuilder sb;
    private static final String TAG_ADDR = "f_addr";
    private static final String TAG_NAME = "f_name";
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    String[] img_link,title;
    Bitmap[] bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(SpringActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.recyclerview_spring);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLinearLayoutManager);

        SeasonFestival information = new SeasonFestival();
        information.execute("봄");

        /*HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(TAG_NAME,"f_name");
        hashMap.put(TAG_ADDR,"f_img");

        arrayList.add(hashMap);

        RecyclerAdapterSeason adapter = new RecyclerAdapterSeason(SpringActivity.this,arrayList,R.layout.activity_spring);
        Log.e("onCreate[arrayList]", "" + arrayList.size());
        rv.setAdapter(adapter);*/

    }


    class SeasonFestival extends AsyncTask<String,Void,String>{
        ProgressDialog loading;


        @Override
        protected String doInBackground(String... strings) {
            final String link = pub_ip + "getSeasonFestival.php";
            String season = strings[0];
            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                String data = "season="+season;
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();
                wr.close();

                int responseStatusCode = conn.getResponseCode();
                Log.d("TitleStudio", "response code - " + responseStatusCode);
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream =conn.getInputStream();
                }
                else {
                    inputStream = conn.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


                sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                System.out.println(sb.toString());
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(SpringActivity.this, "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            loading.dismiss();
            System.out.println("Result : " + result);
            if(result!=null){
                try {
                    JSONObject jsonObject_spring = new JSONObject(result);
                    JSONArray jsonArray_spring = jsonObject_spring.getJSONArray("SeasonFesival");
                    img_link = new String[jsonArray_spring.length()];
                    title = new String[jsonArray_spring.length()];


                    for(int i=0;i<jsonArray_spring.length();i++){
                        JSONObject item = jsonArray_spring.getJSONObject(i);
                        String f_name = item.getString("f_name");
                        String f_img = item.getString("f_img");
                        String f_addr = item.getString("f_addr");

                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("f_name",f_name);
                        hashMap.put("f_img",f_img);
                        hashMap.put("f_addr",f_addr);

                        arrayList.add(hashMap);
                        img_link[i] = f_img;

                    }
                    getImgfromURL(img_link);
                } catch (JSONException e) {
                    Log.d("TitleStudioActivity", "showResult : ", e);

                }
            }
        }

    }
    public void getImgfromURL(final String[] img_link){
        Thread mThread = new Thread(){
            public void run(){
                try {
                    bitmap = new Bitmap[img_link.length];
                    for(int i = 0; i< img_link.length;i++){
                        URL url = new URL(img_link[i]);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        bitmap[i] = BitmapFactory.decodeStream(is);
                        //System.out.println("bitmap["+i+"] : "+ bitmap[i]);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
        try {
            mThread.join();
            RecyclerAdapterSeason adapter = new RecyclerAdapterSeason(SpringActivity.this,arrayList,bitmap,R.layout.activity_spring);
            Log.e("onCreate[arrayList]", "" + arrayList.size());
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch ( InterruptedException e){
            e.printStackTrace();
        }

    }

}
