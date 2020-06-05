package com.example.ssadola;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class seasonactivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_season);

        Button spring = (Button) findViewById(R.id.btn_spring);
        spring.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent SpringActivity = new Intent(seasonactivity.this,SpringActivity.class);
                                          startActivity(SpringActivity);
                                      }
                                  }

        );

    }

}
