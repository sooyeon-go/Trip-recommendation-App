package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageStudioActivity extends AppCompatActivity implements OnMapReadyCallback {
    static String pub_ip = "http://52.79.226.131/";
    StringBuilder sb;
    TextView test;
    GoogleMap mMap;
    Geocoder geocoder;
    String plc_nm;
    String sigun_nm;
    String work_nm;
    ImageView img_search,img_search2,img_search3;
    String[] img_link;
    Bitmap[] bitmap;
    int count;
    ArrayList<HashMap<String, String>> chkArrayList;

    private static String TAG_EMAIL= "u_email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_studio);

        bitmap = new Bitmap[5];
        img_link = new String[5];
        test = findViewById(R.id.tv_test);
        img_search = findViewById(R.id.img_search);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        work_nm = bundle.getString("work_nm");
        plc_nm = bundle.getString("plc_nm");
        sigun_nm = bundle.getString("sigun_nm");
        test.setText(sigun_nm+" "+plc_nm+" "+work_nm);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getImgAPI getimg = new getImgAPI();
        getimg.execute();
        ImageButton btn_star=findViewById(R.id.btn_star);
        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkArrayList = GetLoginData();
                if (chkArrayList == null) {
                    Toast.makeText(ImageStudioActivity.this, "로그인 먼저 해주세요", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(ImageStudioActivity.this, LoginActivity.class);
                    startActivity(login);
                    finish();
                } else {
                    //DB에 여행지 정보 저장하기
                    //로그인 정보->chkArrayList
                    // u_id / theme /addr /work_nm 보내서 저장
                    HashMap<String, String> LoginhashMap = chkArrayList.get(0);
                    String mu_email = LoginhashMap.get(TAG_EMAIL);
                    String mtheme = "studio";
                    String maddr = sigun_nm + plc_nm;
                    String mwork_nm = work_nm;

                    try {
                        InsertBookmark(mu_email, mtheme, maddr, mwork_nm);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        img_search.setOnTouchListener(new OnSwipeTouchListener(ImageStudioActivity.this) {
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                switch(count){
                    case 0:
                        img_search.setImageBitmap(bitmap[0]);
                        count = 1;
                        break;
                    case 1:
                        img_search.setImageBitmap(bitmap[1]);
                        count = 2;
                        break;
                    case 2:
                        img_search.setImageBitmap(bitmap[2]);
                        count = 3;
                        break;
                    case 3 :
                        img_search.setImageBitmap(bitmap[3]);
                        count = 4;
                        break;
                    case 4:
                        img_search.setImageBitmap(bitmap[4]);
                        count = 0;
                        break;
                }
                /*if (count == 0) {
                    img_search.setImageBitmap(bitmap[0]);
                    count = 1;
                } else if(count == 1){
                    img_search.setImageBitmap(bitmap[1]);
                    count = 2;
                }else {
                    img_search.setImageBitmap(bitmap[2]);
                    count = 0;
                }*/
            }

            public void onSwipeLeft() {
                switch(count){
                    case 0:
                        img_search.setImageBitmap(bitmap[0]);
                        count = 1;
                        break;
                    case 1:
                        img_search.setImageBitmap(bitmap[1]);
                        count = 2;
                        break;
                    case 2:
                        img_search.setImageBitmap(bitmap[2]);
                        count = 3;
                        break;
                    case 3 :
                        img_search.setImageBitmap(bitmap[3]);
                        count = 4;
                        break;
                    case 4:
                        img_search.setImageBitmap(bitmap[4]);
                        count = 0;
                        break;
                }
                /*if (count == 0) {
                    img_search.setImageBitmap(bitmap[0]);
                    count = 1;
                } else if(count == 1){
                    img_search.setImageBitmap(bitmap[1]);
                    count = 2;
                }else {
                    img_search.setImageBitmap(bitmap[2]);
                    count = 0;
                }*/
            }

            public void onSwipeBottom() {
            }

        });

    }
    class getImgAPI extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ImageStudioActivity.this, "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loading.dismiss();
            if(result == null){
                Toast.makeText(ImageStudioActivity.this,"resut is null",Toast.LENGTH_LONG).show();
            }else{
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Toast.makeText( ImageStudioActivity.this,result,Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for(int i=0;i<5;i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        img_link[i] = item.getString("link");
                        //Toast.makeText(ImageStudioActivity.this,link,Toast.LENGTH_LONG).show();
                    }
                    getImgfromURL(img_link);
                } catch (JSONException e) {
                    Log.d("ImageStudioActivity", "showResult : ", e);
                    //Toast.makeText(ImageStudioActivity.this,"이미지 없음",Toast.LENGTH_LONG).show();
                }
            }
        }
        @Override
        protected String doInBackground(String... strings) {
            String clientId = "LxkS0ViAnnFAF25uSHYg";//애플리케이션 클라이언트 아이디값";
            String clientSecret = "VTz9G4Wmfz";//애플리케이션 클라이언트 시크릿값";
            try {
                String query = URLEncoder.encode(sigun_nm+" "+plc_nm,"UTF-8");
                String display = URLEncoder.encode("display","UTF-8");
                String sort = URLEncoder.encode("sort","UTF-8");
                String filter = URLEncoder.encode("filter","UTF-8");
                String apiURL = "https://openapi.naver.com/v1/search/image?query="+query+"&"+display+"="+ URLEncoder.encode("5","UTF-8")
                +"&"+sort+"="+URLEncoder.encode("sim","UTF-8")+"&filter="+filter+ URLEncoder.encode("medium","UTF-8");//display+sort;

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
                return response.toString();
            } catch (Exception e) {
                System.out.println(e);
                return new String("Exception: " + e.getMessage());
            }
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(
                    sigun_nm+plc_nm,
                    10);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(addressList.get(0).toString());

        String []splitStr = addressList.get(0).toString().split(",");
        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2);
        System.out.println(address);

        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1);
        String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1);
        System.out.println(latitude);
        System.out.println(longitude);

        // 좌표(위도, 경도) 생성
        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        // 마커 생성
        MarkerOptions mOptions = new MarkerOptions();
        mOptions.title("search result");
        mOptions.snippet(address);
        mOptions.position(point);
        // 마커 추가
        mMap.addMarker(mOptions);
        // 해당 좌표로 화면 줌
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
    }
    public void getImgfromURL(final String[] img_link){
        Thread mThread = new Thread(){
            public void run(){
                try {
                    for(int i = 0; i< img_link.length;i++){
                        URL url = new URL(img_link[i]);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        bitmap[i] = BitmapFactory.decodeStream(is);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };

        mThread.start();
        try {
            mThread.join();
            img_search.setImageBitmap(bitmap[0]);
        }catch ( InterruptedException e){
                e.printStackTrace();
        }
    }
    public ArrayList<HashMap<String, String>> GetLoginData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("UserInfo", "EMPTY");
        if (json.equals("EMPTY")) {
            Toast.makeText(ImageStudioActivity.this, "로그인 먼저 해주세요", Toast.LENGTH_LONG).show();
            return null;
        }
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void InsertBookmark(String... params) throws UnsupportedEncodingException {
        String u_email = (String) params[0];
        String theme = (String) params[1];
        String addr = (String) params[2];
        String work_nm = (String) params[3];

        final String link = pub_ip + "Bookmark_Theme.php";
        final String data = "u_email=" + u_email + "&theme=" + theme + "&addr=" + addr + "&work_nm=" + work_nm;

        Thread mThread = new Thread() {
            public void run() {
                try {
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();
                    wr.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();
        try {
            mThread.join();
            Toast.makeText(ImageStudioActivity.this,sb.toString(),Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
