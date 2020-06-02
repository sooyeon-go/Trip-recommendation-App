package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    Geocoder geocoder;
    String tmp;
    String[] course;
    ArrayList<String> location = new ArrayList<>();
    ArrayList<LatLng> position = new ArrayList<>();
    List<String> listView_loc = new ArrayList<>();
    int size;
    private  ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tmp = bundle.getString("course");
        tmp = tmp.replaceAll("\"","");
        for(int i =0; i< tmp.length();i++){
            course = tmp.split(",");
        }

        size = course.length;

        mListView = findViewById(R.id.listView_course);
        /*ArrayAdapter adapter = new ArrayAdapter(ResultDetailActivity.this, android.R.layout.simple_list_item_1, listView_loc) ;
        mListView.setAdapter(adapter);*/
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
                    .title(location.get(i)); // 타이틀.

            // 2. 마커 생성 (마커를 나타냄)
            mMap.addMarker(makerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position.get(i),15));
        }
        ArrayAdapter adapter = new ArrayAdapter(ResultDetailActivity.this, android.R.layout.simple_list_item_1, listView_loc) ;
        mListView.setAdapter(adapter);
    }

    //마커정보창 클릭리스너는 다작동하나, 마커클릭리스너는 snippet정보가 있으면 중복되어 이벤트처리가 안되는거같다.
    // oneMarker(); 는 동작하지않으나 manyMarker(); 는 snippet정보가 없어 동작이가능하다.

    //정보창 클릭 리스너
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = marker.getId();
            Toast.makeText(ResultDetailActivity.this, "정보창 클릭 Marker ID : "+markerId, Toast.LENGTH_SHORT).show();
        }
    };

    //마커 클릭 리스너
    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String markerId = marker.getId();
            //선택한 타겟위치
            LatLng location = marker.getPosition();
            Toast.makeText(ResultDetailActivity.this, "마커 클릭 Marker ID : "+markerId+"("+location.latitude+" "+location.longitude+")", Toast.LENGTH_SHORT).show();
            return false;
        }
    };

    public void GeoCoding(String loc){
        double lowerLeftLatitude,lowerLeftLongitude,upperRightLatitude,upperRightLongitude;
        lowerLeftLatitude = 33; lowerLeftLongitude = 124;
        upperRightLatitude = 43; upperRightLongitude = 132;
        List<Address> addressList = null;

        try {
            addressList = geocoder.getFromLocationName(
                    loc,
                    10,
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
    }

}
