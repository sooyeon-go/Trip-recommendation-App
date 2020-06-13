package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    Geocoder geocoder;
    String tmp;
    String[] course;
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
                if(mBookmark_detail.isSelected()){
                    mBookmark_detail.setSelected(false);
                    //bookmark_delete.php
                }else{
                    mBookmark_detail.setSelected(true);
                    //bookmark_insert.php
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

}
