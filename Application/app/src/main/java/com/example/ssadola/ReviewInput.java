package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;


import java.util.ArrayList;

public class ReviewInput extends AppCompatActivity {
    static String pub_ip = "http://15.165.95.187/";
    private Spinner spinner;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    private TextView Q1, Q2, Q3, Q4, Q5, Q6;
    private EditText A1, A2, A3;
    private RatingBar ratingBar;
    private CheckBox check1, check2, check3, check4, check5;
    private Button button;
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
        button.setOnClickListener(new View.OnClickListener() {//버튼 이벤트 처리
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"리뷰가 등록되었습니다",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ReviewInput.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
