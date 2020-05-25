package com.example.ssadola;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.util.ViewAnimator;


public class CustomFragment extends Fragment implements ScreenShotable {
    static String pub_ip = "http://52.79.226.131/";
    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    RatingBar q1_rating,q2_rating,q3_rating,q4_rating,q5_rating,q6_rating,q7_rating,q8_rating;
    StringBuilder sb;

    public static CustomFragment newInstance(int resId) {
        CustomFragment customfragment = new CustomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        customfragment.setArguments(bundle);
        return customfragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
        setRatingBar(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_custom, container, false);
        /*mImageView = (ImageView) rootView.findViewById(R.id.image_content);
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setImageResource(res);*/

        FloatingActionButton fab = rootView.findViewById(R.id.Floating1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetRating();
                Intent result = new Intent(getActivity(),ResultActivity.class);
                startActivity(result);
            }
        });
        return rootView;
    }
    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    public void setRatingBar(View view){
        q1_rating = view.findViewById(R.id.Q1_rating);
        q2_rating = view.findViewById(R.id.Q2_rating);
        q3_rating = view.findViewById(R.id.Q3_rating);
        q4_rating = view.findViewById(R.id.Q4_rating);
        q5_rating = view.findViewById(R.id.Q5_rating);
        q6_rating = view.findViewById(R.id.Q6_rating);
        q7_rating = view.findViewById(R.id.Q7_rating);
        q8_rating = view.findViewById(R.id.Q8_rating);
    }
    public void GetRating(){
        CheckRating();

        final float mq1 = q1_rating.getRating()*2;
        final float mq2 = q2_rating.getRating()*2;
        final float mq3 = q3_rating.getRating()*2;
        final float mq4 = q4_rating.getRating()*2;
        final float mq5 = q5_rating.getRating()*2;
        final float mq6 = q6_rating.getRating()*2;
        final float mq7 = q7_rating.getRating()*2;
        final float mq8 = q8_rating.getRating()*2;

        Thread mThread = new Thread(){
            public void run(){
                try {

                    URL url = new URL(pub_ip+"user_Survey.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);;
                    conn.connect();

                    String data = URLEncoder.encode("Q1", "UTF-8") + "=" +URLEncoder.encode(Float.toString(mq1),"UTF-8");
                    data += "&" + URLEncoder.encode("Q2", "UTF-8") + "=" + URLEncoder.encode(Float.toString(mq2),"UTF-8");
                    data += "&" + URLEncoder.encode("Q3", "UTF-8") + "=" + URLEncoder.encode(Float.toString(mq3),"UTF-8");
                    data += "&" + URLEncoder.encode("Q4", "UTF-8") + "=" + URLEncoder.encode(Float.toString(mq4),"UTF-8");
                    data += "&" + URLEncoder.encode("Q5", "UTF-8") + "=" + URLEncoder.encode(Float.toString(mq5),"UTF-8");
                    data += "&" + URLEncoder.encode("Q6", "UTF-8") + "=" + URLEncoder.encode(Float.toString(mq6),"UTF-8");
                    data += "&" + URLEncoder.encode("Q7", "UTF-8") + "=" + URLEncoder.encode(Float.toString(mq7),"UTF-8");
                    data += "&" + URLEncoder.encode("Q8", "UTF-8") + "=" + URLEncoder.encode(Float.toString(mq8),"UTF-8");


                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    wr.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

        try {
            mThread.join();
            Toast.makeText(getActivity(),sb.toString(),Toast.LENGTH_LONG).show();
        }catch ( InterruptedException e){
            e.printStackTrace();
        }
    }
    public void CheckRating(){
        if(q1_rating.getRating() == 0)
            q1_rating.setRating(0.5f);
        if(q2_rating.getRating() == 0)
            q2_rating.setRating(0.5f);
        if(q3_rating.getRating() == 0)
            q3_rating.setRating(0.5f);
        if(q4_rating.getRating() == 0)
            q4_rating.setRating(0.5f);
        if(q4_rating.getRating() == 0)
            q4_rating.setRating(0.5f);
        if(q5_rating.getRating() == 0)
            q5_rating.setRating(0.5f);
        if(q6_rating.getRating() == 0)
            q6_rating.setRating(0.5f);
        if(q7_rating.getRating() == 0)
            q7_rating.setRating(0.5f);
        if(q8_rating.getRating() == 0)
            q8_rating.setRating(0.5f);
    }

}
