package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {
    private ListView listView,listView2;
    //ArrayList<HashMap<String,String>> listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listView = findViewById(R.id.lv_dupli);
        listView2 = findViewById(R.id.lv_content);
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        SetData(result);
    }

    private void SetData(String result){
        Recomm_ResultAdapter resultAdapter_dupli = new Recomm_ResultAdapter();
        Recomm_ResultAdapter resultAdapter_content = new Recomm_ResultAdapter();
        setDupli(result,resultAdapter_dupli);
        setContentBased(result,resultAdapter_content);
        
        listView.setAdapter(setDupli(result,resultAdapter_dupli));
        listView2.setAdapter(setContentBased(result,resultAdapter_content));
    }

    private Recomm_ResultAdapter setDupli(String result, Recomm_ResultAdapter resultAdapter_dupli){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("dupli");
            for(int i=0;i<jsonArray.length();i++){
                String course = jsonArray.get(i).toString();
                resultAdapter_dupli.addItem("dupli_" + i,  course);

            }
            return resultAdapter_dupli;
        } catch (JSONException e) {
            Log.d("ResultActivity", "setDupli : ", e);
            return null;
        }
    }
    private Recomm_ResultAdapter setContentBased(String result,Recomm_ResultAdapter resultAdapter_content){
        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("content_based");
            for(int i=0;i<jsonArray.length();i++){
                String course = jsonArray.get(i).toString();
                resultAdapter_content.addItem("content_based" + i, course);

            }
            return resultAdapter_content;
        } catch (JSONException e) {
            Log.d("ResultActivity", "setContentBased : ", e);
            return null;
        }
    }
}
