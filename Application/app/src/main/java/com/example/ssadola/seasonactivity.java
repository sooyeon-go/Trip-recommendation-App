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

        Button summer = (Button) findViewById(R.id.btn_summer);
        spring.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent SummerActivity = new Intent(seasonactivity.this,SummerActivity.class);
                                          startActivity(SummerActivity);
                                      }
                                  }

        );

        Button fall = (Button) findViewById(R.id.btn_fall);
        spring.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent FallActivity = new Intent(seasonactivity.this, FallActivity.class);
                                          startActivity(FallActivity);
                                      }
                                  }

        );

        Button winter = (Button) findViewById(R.id.btn_winter);
        spring.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent WinterActivity = new Intent(seasonactivity.this, com.example.ssadola.WinterActivity.class);
                                          startActivity(WinterActivity);
                                      }
                                  }

        );

    }

}
