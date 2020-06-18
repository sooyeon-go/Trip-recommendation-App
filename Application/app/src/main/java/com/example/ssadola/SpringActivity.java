package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SpringActivity extends AppCompatActivity {


    static String pub_ip = "http://15.165.95.187/";
    StringBuilder sb;

    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private boolean mShowingFragments = false;
    String[] img_link,title;
    Bitmap[] bitmap;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring);

        Studio_Poster information = new Studio_Poster();
        information.execute();



        /*TextView titleView = (TextView) findViewById(R.id.tv_springfest_title);
        TextView contentView = (TextView) findViewById(R.id.tv_springfest_content);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select title, content from tb_festival" +
                "             order by _id desc limit 1", null);

        while (cursor .moveToNext()){
            titleView.setText(cursor.getString(0));
            contentView.setText(cursor.getString(1));

        }
        db.close();
        DB 에서 들ㅇ고 오려고 짜놓은 쿼리문이긴 합니다만,,,,?*/
    }


    class Studio_Poster extends AsyncTask<Void,Void,String>{
        ProgressDialog loading;


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
                    JSONArray jsonArray_spring = jsonObject_spring.getJSONArray("Studio_Info");
                    img_link = new String[jsonArray_spring.length()];
                    title = new String[jsonArray_spring.length()];


                    for(int i=0;i<jsonArray_spring.length();i++){
                        JSONObject item = jsonArray_spring.getJSONObject(i);
                        String festspring_title = item.getString("festspring_title");
                        String festspring_img = item.getString("festspring_img");
                        String festspring_name = item.getString("festspring_name");

                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("work_title",festspring_title);
                        hashMap.put("img",festspring_img);

                        arrayList.add(hashMap);
                        img_link[i] = festspring_img;

                    }
                    getImgfromURL(img_link);
                } catch (JSONException e) {
                    Log.d("TitleStudioActivity", "showResult : ", e);

                }
            }
        }
        @Override
        protected String doInBackground(Void... voids) {
            final String link = pub_ip + "Studio_Poster.php";

            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

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

    }

}
