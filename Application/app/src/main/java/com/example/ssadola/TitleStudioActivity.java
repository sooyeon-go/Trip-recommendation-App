package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class TitleStudioActivity extends AppCompatActivity {

    static String pub_ip = "http://15.165.95.187/";
    StringBuilder sb;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
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
        setContentView(R.layout.activity_title_studio);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        Studio_Poster info = new Studio_Poster();
        info.execute();

    }

    /**
     * Change value in dp to pixels
     * @param dp
     * @param context
     * @return
     */
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    class Studio_Poster extends AsyncTask<Void,Void,String>{
        ProgressDialog loading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(TitleStudioActivity.this, "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            loading.dismiss();
            System.out.println("Result : " + result);
            if(result!=null){
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("Studio_Info");
                    img_link = new String[jsonArray.length()];
                    title = new String[jsonArray.length()];
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        String i_title = item.getString("work_title");
                        String i_img = item.getString("img");

                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("work_title",i_title);
                        hashMap.put("img",i_img);

                        arrayList.add(hashMap);
                        img_link[i] = i_img;

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

        mThread.start();
        try {
            mThread.join();
            makeCard(arrayList,bitmap);
        }catch ( InterruptedException e){
            e.printStackTrace();
        }
    }
    public void makeCard(ArrayList<HashMap<String,String>> arrayList,Bitmap[] bm){
        mCardAdapter = new CardPagerAdapter();
        for(int i = 0; i < arrayList.size(); i++){
            HashMap<String, String> tmp = arrayList.get(i);
            mCardAdapter.addCardItem(new CardItem(tmp.get("work_title"),bm[i]));
        }

        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }
}
