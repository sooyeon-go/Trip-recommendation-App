package com.example.ssadola;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class seasonactivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_season);

        ImageButton spring = (ImageButton) findViewById(R.id.btn_spring);
        spring.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent SpringActivity = new Intent(seasonactivity.this,SpringActivity.class);
                                          startActivity(SpringActivity);
                                      }
                                  }

        );

        ImageButton summer = (ImageButton) findViewById(R.id.btn_summer);
        summer.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent SummerActivity = new Intent(seasonactivity.this,SummerActivity.class);
                                          startActivity(SummerActivity);
                                      }
                                  }

        );

        ImageButton fall = (ImageButton) findViewById(R.id.btn_fall);
        fall.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent FallActivity = new Intent(seasonactivity.this, FallActivity.class);
                                          startActivity(FallActivity);
                                      }
                                  }

        );

        ImageButton winter = (ImageButton) findViewById(R.id.btn_winter);
        winter.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent WinterActivity = new Intent(seasonactivity.this, com.example.ssadola.WinterActivity.class);
                                          startActivity(WinterActivity);
                                      }
                                  }

        );

    }

}
