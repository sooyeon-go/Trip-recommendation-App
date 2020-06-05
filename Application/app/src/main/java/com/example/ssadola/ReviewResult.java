package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.content.Intent;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.os.AsyncTask;
import android.app.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class ReviewResult extends AppCompatActivity {
    private static String pub_ip = "http://15.165.95.187/";
    private Button button;

    private static final String TAG_HOTEL = "hotel";
    private static final String TAG_SIGHT = "sight";
    private static final String TAG_EAT = "eat";
    private static final String TAG_PLACE = "place";
    private static final String TAG_RATING = "rating";
    private static final String TAG_SPEC = "spec";

    //private ArrayList<PersonalData> personList;
    private ArrayList<HashMap<String,String>> personList;
    private UsersAdapter newAdapter;
    private RecyclerView newRecycle;

    private static final String TAG_JSON="webnautes";
    private static String TAG = "리뷰 테스트";
    private String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_list);
        button = (Button) findViewById(R.id.button);
        //newAdapter = new UsersAdapter(this, personList);
        personList = new ArrayList<>();
        newRecycle = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ReviewResult.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newRecycle.setLayoutManager(mLinearLayoutManager);

        /*newAdapter = new UsersAdapter(this, personList);
        newRecycle.setAdapter(newAdapter);*/

        //personList.clear();
        //newAdapter.notifyDataSetChanged();
        GetData task = new GetData();
        task.execute(pub_ip + "review_get.php");
    }
    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReviewResult.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);
            if (result == null) {
            } else {
                mJsonString = result;
                System.out.println(mJsonString);
                showResult();
            }
        }
        @Override
        protected String doInBackground(String... params){
            String serverURL = params[0];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
    }
    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String hotel = item.getString(TAG_HOTEL);
                String sight = item.getString(TAG_SIGHT);
                String eat = item.getString(TAG_EAT);
                String place = item.getString(TAG_PLACE);
                String rating = item.getString(TAG_RATING);
                String spec = item.getString(TAG_SPEC);

                /*PersonalData personalData = new PersonalData();

                personalData.setMember_hotel(hotel);
                personalData.setMember_sight(sight);
                personalData.setMember_eat(eat);
                personalData.setMember_place(place);
                personalData.setMember_rating(rating);
                personalData.setMember_spec(spec);*/
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(TAG_HOTEL,hotel);
                hashMap.put(TAG_SIGHT,sight);
                hashMap.put(TAG_EAT,eat);
                hashMap.put(TAG_PLACE,place);
                hashMap.put(TAG_RATING,rating);
                hashMap.put(TAG_SPEC,spec);
                personList.add(hashMap);
                //System.out.println(personList);
                //newAdapter.notifyDataSetChanged();
            }
            /*newAdapter = new UsersAdapter(this, personList);
            newRecycle.setAdapter(newAdapter);
            newAdapter.notifyDataSetChanged();*/

            UsersAdapter adapter = new UsersAdapter(ReviewResult.this,personList);
            Log.e("onCreate[personList]", "" + personList.size());
            newRecycle.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewResult.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
