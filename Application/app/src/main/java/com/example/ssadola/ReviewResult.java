package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.os.Bundle;
import android.content.Intent;


public class ReviewResult extends AppCompatActivity {
    private static String pub_ip = "http://15.165.95.187/";
    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_list);
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReviewResult.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
