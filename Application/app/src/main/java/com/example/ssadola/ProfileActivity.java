package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_id,tv_name,tv_email,tv_age,tv_sex;
    Button btn_edit,btn_logout,btn_home;
    ArrayList<HashMap<String, String>> arrayList;

    private static String TAG_NAME = "u_name";
    private static String TAG_AGE = "age";
    private static String TAG_SEX = "sex";
    private static String TAG_EMAIL= "u_email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_age = (TextView)findViewById(R.id.tv_age);
        tv_sex = (TextView)findViewById(R.id.tv_sex);
        tv_email = (TextView)findViewById(R.id.tv_email);

        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //추후에 추가
                Toast.makeText(ProfileActivity.this,"추후에 추가예정",Toast.LENGTH_LONG).show();
            }
        });
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.clear();
                editor.commit();
                Toast.makeText(ProfileActivity.this,"로그아웃",Toast.LENGTH_SHORT).show();
                Intent BackToMain = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(BackToMain);

            }
        });

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(home);
                finish();
            }
        });
        arrayList = GetLoginData();
        if(arrayList != null){
            HashMap<String,String> hashMap = arrayList.get(0);

            tv_name.setText(hashMap.get(TAG_NAME));
            tv_age.setText(hashMap.get(TAG_AGE));
            tv_sex.setText(hashMap.get(TAG_SEX));
            tv_email.setText(hashMap.get(TAG_EMAIL));
        }else{
            Toast.makeText(ProfileActivity.this,"로그인 먼저 해주세요",Toast.LENGTH_LONG).show();
        }
    }


    public ArrayList<HashMap<String, String>> GetLoginData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("UserInfo", "EMPTY");
        if(json.equals("EMPTY")){
            Toast.makeText(ProfileActivity.this,"로그인 먼저 해주세요",Toast.LENGTH_LONG).show();
            return null;
        }
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
