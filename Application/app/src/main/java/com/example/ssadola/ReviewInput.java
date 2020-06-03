package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.ListAdapter;
import android.util.Log;

import java.util.ArrayList;

public class ReviewInput extends AppCompatActivity {
    private Spinner spinner;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    private TextView Q1, Q2, Q3, Q4, Q5, Q6;
    private EditText A1, A2, A3;
    private RatingBar ratingBar;
    private CheckBox check1, check2, check3, check4, check5;
    private Button button;

    private final String dbName = "review_db";
    private final String tableName = "reviewtable";

    SQLiteDatabase sampleDB = null;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewinput);

        ratingBar =(RatingBar)findViewById(R.id.ratingBar);
        spinner = (Spinner)findViewById(R.id.spinner);
        Q1 = (TextView)findViewById(R.id.Q1);
        Q2 = (TextView)findViewById(R.id.Q2);
        Q3 = (TextView)findViewById(R.id.Q3);
        Q4 = (TextView)findViewById(R.id.Q4);
        Q5 = (TextView)findViewById(R.id.Q5);
        Q6 = (TextView)findViewById(R.id.Q6);
        A1 = (EditText)findViewById(R.id.A1);
        A2 = (EditText)findViewById(R.id.A2);
        A3 = (EditText)findViewById(R.id.A3);
        check1 = (CheckBox)findViewById(R.id.check1);
        check2 = (CheckBox)findViewById(R.id.check2);
        check3 = (CheckBox)findViewById(R.id.check3);
        check4 = (CheckBox)findViewById(R.id.check4);
        check5 = (CheckBox)findViewById(R.id.check5);
        button = (Button)findViewById(R.id.button);

        arrayList = new ArrayList<>();
        arrayList.add("경기도");
        arrayList.add("강원도");
        arrayList.add("전라도");
        arrayList.add("충청도");
        arrayList.add("경상도");
        arrayList.add("제주도");
        arrayList.add("서울");
        arrayList.add("인천");
        arrayList.add("대전");
        arrayList.add("부산");
        arrayList.add("광주");
        arrayList.add("울산");
        arrayList.add("대구");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);

        spinner.setAdapter(arrayAdapter);
        try {

            sampleDB = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
                    + " (hotel TEXT, sight TEXT, eat TEXT, place TEXT, rating TEXT, result TEXT);");
            sampleDB.execSQL("INSERT INTO " + tableName
                    + "(hotel, sight, eat, place, rating, result)  Values ('신라 호텔', '광장 시장', '더 파크뷰', '서울', '7', '호캉스')");

        }catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotel = A1.getText().toString();
                String sight = A2.getText().toString();
                String eat = A3.getText().toString();
                String place = spinner.getSelectedItem().toString();
                float rating = ratingBar.getRating();
                String result = "";

                if(check1.isChecked() == true) result += check1.getText().toString();
                if(check2.isChecked() == true) result += check2.getText().toString();
                if(check3.isChecked() == true) result += check3.getText().toString();
                if(check4.isChecked() == true) result += check4.getText().toString();
                if(check5.isChecked() == true) result += check5.getText().toString();

                try {

                    sampleDB.execSQL("INSERT INTO " + tableName
                            + "(hotel, sight, eat, place, rating, result)  Values ('" + hotel + "','" + sight + "','" + eat + "','" + place + "','" + rating + "','" + result + "');");
                    sampleDB.close();

                }catch (SQLiteException se) {
                    Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("", se.getMessage());
                }

        Toast.makeText(getApplicationContext(),"리뷰가 등록되었습니다",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(ReviewInput.this,MainActivity.class);
        startActivity(intent);
            }
        });
    }
}

