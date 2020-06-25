package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        ImageButton studio = findViewById(R.id.btn_studoi_set);
        studio.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent studio_title = new Intent(ThemeActivity.this,TitleStudioActivity.class);
                                          startActivity(studio_title);
                                      }
                                  }

        );
        ImageButton season = findViewById(R.id.btn_season);
        season.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent season_activity = new Intent(ThemeActivity.this, seasonactivity.class);
                                          startActivity(season_activity);
                                      }
                                  }

        );
    }
}
