package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.util.ArrayList;

public class ReviewInput extends AppCompatActivity {
    private static String pub_ip = "http://15.165.95.187/";
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

                InsertAsync task = new InsertAsync();
                task.execute(hotel, sight, eat, place, String.valueOf(rating), result);

                Toast.makeText(getApplicationContext(),"리뷰가 등록되었습니다",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ReviewInput.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private class InsertAsync extends AsyncTask<String, Void, String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ReviewInput.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                String hotel = (String) params[0];
                String sight = (String) params[1];
                String eat = (String) params[2];
                String place = (String) params[3];
                String rating = (String) params[4];
                String result = (String) params[5];

                String link = pub_ip + "Review_data.php";
                String data = URLEncoder.encode("hotel", "UTF-8") + "=" + URLEncoder.encode(hotel, "UTF-8");
                data += "&" + URLEncoder.encode("sight", "UTF-8") + "=" + URLEncoder.encode(sight, "UTF-8");
                data += "&" + URLEncoder.encode("eat", "UTF-8") + "=" + URLEncoder.encode(eat, "UTF-8");
                data += "&" + URLEncoder.encode("place", "UTF-8") + "=" + URLEncoder.encode(place, "UTF-8");
                data += "&" + URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(rating, "UTF-8");
                data += "&" + URLEncoder.encode("result", "UTF-8") + "=" + URLEncoder.encode(result, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();
                wr.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
    }
}
