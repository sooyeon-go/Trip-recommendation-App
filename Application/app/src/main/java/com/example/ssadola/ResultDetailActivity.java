package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
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

public class ResultDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    Geocoder geocoder;
    String tmp;
    String[] course;
    static String pub_ip = "http://15.165.95.187/";
    ArrayList<HashMap<String, String>> arrayList;
    ArrayList<String> location = new ArrayList<>();
    ArrayList<LatLng> position = new ArrayList<>();
    ArrayList<LatLng> line_points = new ArrayList<>();

    List<String> listView_loc = new ArrayList<>();
    List<String> list_address = new ArrayList<>();
    int size;
    private  ListView mListView;
    private ImageButton mBookmark_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tmp = bundle.getString("course");
        for(int i =0; i< tmp.length();i++){
            course = tmp.split(",");
        }

        size = course.length;
        mBookmark_detail = findViewById(R.id.ic_bookmark_resultDetail);
        mBookmark_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList = GetLoginData();
                if(arrayList == null){
                    Toast.makeText(v.getContext(),"로그인 먼저 해주세요",Toast.LENGTH_LONG).show();
                    Intent login = new Intent(v.getContext(),LoginActivity.class);
                    startActivity(login);
                    //v.getContext().finish();
                }
                else{
                    HashMap<String,String> hashMap = arrayList.get(0);
                    String u_email = hashMap.get("u_email");
                    if(mBookmark_detail.isSelected()){
                        mBookmark_detail.setSelected(false);
                        //bookmark_delete.php
                        Bookmark_delete delete = new Bookmark_delete();
                        delete.execute(u_email);
                    }else{
                        mBookmark_detail.setSelected(true);
                        //bookmark_insert.php

                        Bookmark_insert insert = new Bookmark_insert();
                        insert.execute(u_email);
                    }
                }
            }
        });
        mListView = findViewById(R.id.listView_course);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_course);
        mapFragment.getMapAsync(this); //getMapAsync must be called on the main thread.
    }
    public ArrayList<HashMap<String, String>> GetLoginData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ResultDetailActivity.this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("UserInfo", "EMPTY");
        if (json.equals("EMPTY")) {
            //Toast.makeText(BookmarkActivity.this,"로그인 먼저 해주세요",Toast.LENGTH_LONG).show();
            return null;
        }
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
        @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);
        for(int i = 0; i < size; i++) {
           GeoCoding(course[i]);
        }

        for(int i = 0; i < position.size();i++){
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(position.get(i))
                    .snippet(list_address.get(i))
                    .title(location.get(i)); // 타이틀.

            line_points.add(position.get(i));
            // 2. 마커 생성 (마커를 나타냄)
            mMap.addMarker(makerOptions);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position.get(0),12));

        PolylineOptions line = new PolylineOptions();
        line.color(Color.BLUE);
        line.width(5);
        line.addAll(line_points);
        mMap.addPolyline(line);


        ArrayAdapter adapter = new ArrayAdapter(ResultDetailActivity.this, android.R.layout.simple_list_item_1, listView_loc) ;
        mListView.setAdapter(adapter);
    }


    public void GeoCoding(String loc){
        double lowerLeftLatitude,lowerLeftLongitude,upperRightLatitude,upperRightLongitude;
        lowerLeftLatitude = 33; lowerLeftLongitude = 124;
        upperRightLatitude = 43; upperRightLongitude = 132;
        List<Address> addressList = null;

        try {
            addressList = geocoder.getFromLocationName(
                    loc,
                    5,
                    lowerLeftLatitude,
                    lowerLeftLongitude,
                    upperRightLatitude,
                    upperRightLongitude);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("loc : "+loc);
        if(addressList.size()==0){
            Log.e("GeoCoding() : ","검색 결과 없음");
            return;
        }
        System.out.println(addressList.get(0).toString());

        String []splitStr = addressList.get(0).toString().split(",");
        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2);
        System.out.println(address);

        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1);
        String longitude= splitStr[12].substring(splitStr[12].indexOf("=") + 1);
        System.out.println(latitude);
        System.out.println(longitude);

        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        position.add(point);
        location.add(loc);

        listView_loc.add(loc);

        list_address.add(address);
    }
    class Bookmark_insert extends AsyncTask<String, Void, String> {
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //loading.dismiss();
            System.out.println("Result : " + result);
            if(result!=null){
                Toast.makeText(ResultDetailActivity.this,result,Toast.LENGTH_SHORT).show();
            }else{
                Log.e("ImageStudioFragment","result is null");
            }
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                //u_email,i_title,i_scene,i_location,i_address,mImageUrl
                String u_email = (String) params[0];
                String title = (String) params[1];
                String scene = params[2];
                String location = params[3];
                String address = params[4];
                String image = params[5];

                String link = pub_ip+"Bookmark_insert.php";
                //String data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
                //data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");
                String data = "u_email="+u_email+"&title="+ URLEncoder.encode(title,"UTF-8")
                        + "&scene="+URLEncoder.encode(scene,"UTF-8") + "&location="+URLEncoder.encode(location,"UTF-8")
                        +"&address="+URLEncoder.encode(address,"UTF-8")+"&image="+URLEncoder.encode(image,"UTF-8");

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
    class Bookmark_delete extends AsyncTask<String, Void, String> {
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //loading.dismiss();
            System.out.println("Result : " + result);
            if(result!=null){
                Toast.makeText(ResultDetailActivity.this,result,Toast.LENGTH_SHORT).show();
            }else{
                Log.e("ImageStudioFragment","result is null");
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
                String data = "u_email="+u_email+"&title="+URLEncoder.encode(title,"UTF-8")
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
