package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class BookmarkActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        arrayList = GetLoginData();

        if(arrayList == null){
            Toast.makeText(BookmarkActivity.this,"로그인 먼저 해주세요",Toast.LENGTH_LONG).show();
            Intent login = new Intent(BookmarkActivity.this,LoginActivity.class);
            startActivity(login);
            finish();
        }else{
            //db에서 즐찾한 여행지 정보들 가져와서 보여주기
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
}
