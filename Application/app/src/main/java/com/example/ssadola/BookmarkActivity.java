package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class BookmarkActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> arrayList;
    ArrayList<HashMap<String,String>> BookmarkList;
    TextView test;
    String mJsonString;
    private RecyclerView rv;

    static String pub_ip = "http://15.165.95.187/";
    StringBuilder sb;
    private static final String TAG_EMAIL="u_email";
    private static final String TAG_NAME = "u_name";
    private static final String TAG_THEME = "theme";
    private static final String TAG_ADDR = "addr";
    private static final String TAG_WOKR = "work_nm";
    private static final String TAG_COUNT = "count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        BookmarkList = new ArrayList<>();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(BookmarkActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLinearLayoutManager);
        arrayList = GetLoginData();

        if(arrayList == null){
            Toast.makeText(BookmarkActivity.this,"로그인 먼저 해주세요",Toast.LENGTH_LONG).show();
            Intent login = new Intent(BookmarkActivity.this,LoginActivity.class);
            startActivity(login);
            finish();
        }else{
        //db에서 즐찾한 여행지 정보들 가져와서 보여주기
        //유저정보를 인자로 넘겨주고 디비에서 셀렉해오기
            HashMap<String, String> LoginhashMap = arrayList.get(0);
            String mu_email = LoginhashMap.get(TAG_EMAIL);
        //Toast.makeText(BookmarkActivity.this,mu_email,Toast.LENGTH_LONG).show();
        //String mu_name = LoginhashMap.get(TAG_NAME);
            GetBookmark getbm = new GetBookmark();
            getbm.execute(mu_email);
            }
        }


    public ArrayList<HashMap<String, String>> GetLoginData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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

    class GetBookmark extends AsyncTask<String, Void, String>{

        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(BookmarkActivity.this, "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loading.dismiss();
            if(result == null){
            }else{
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("BookmarkTheme");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        String i_email = item.getString(TAG_EMAIL);
                        //System.out.println(i+" "+i_email);
                        String i_theme = item.getString(TAG_THEME);
                        //System.out.println(i+" "+i_theme);
                        String i_addr = item.getString(TAG_ADDR);
                        //System.out.println(i+" "+i_addr);
                        String i_worknm = item.getString(TAG_WOKR);
                        //System.out.println(i+" "+i_worknm);

                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put(TAG_EMAIL,i_email);
                        hashMap.put(TAG_THEME,i_theme);
                        hashMap.put(TAG_ADDR,i_addr);
                        hashMap.put(TAG_WOKR,i_worknm);
                        hashMap.put(TAG_COUNT,Integer.toString(i+1));

                        BookmarkList.add(hashMap);
                    }
                    RecyclerAdapter adapter = new RecyclerAdapter(BookmarkActivity.this,BookmarkList);
                    Log.e("onCreate[BookmarkList]", "" + BookmarkList.size());
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.d("BookmarkActivity", "showResult : ", e);
                }
            }
        }
        @Override
        protected String doInBackground(String... params) {
            //String name = param[0];
            final String link = pub_ip + "getBookmarkTheme.php";
            String email = params[0];
            final String data = "u_email=" + email;
            try {

                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();
                wr.close();

                int responseStatusCode = conn.getResponseCode();
                //Log.d(TAG, "response code - " + responseStatusCode);
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
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return new String("Exception: " + e.getMessage());
            }
        }
    }
}
