package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

       listView = findViewById(R.id.listView);
//
        SetData();
    }

    private void SetData(){
        MyAdapter mMyAdapter = new MyAdapter();


        for (int i=0; i<10; i++) {
            mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_launcher_background), "name_" + i, "contents_" + i);
        }
        listView.setAdapter(mMyAdapter);
    }
}
