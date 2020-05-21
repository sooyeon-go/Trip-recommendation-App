package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    static String pub_ip = "http://52.79.226.131/";
    private static String TAG = "User_Info";
    private static String TAG_CHK = "pw_chk";
    private static String TAG_NAME = "u_name";
    private static String TAG_AGE = "age";
    private static String TAG_SEX = "sex";
    private static String TAG_EMAIL= "u_email";
    ArrayList<HashMap<String,String>> mArrayList = new ArrayList<>();
    private View containerView;
    protected int res;
    ImageView imageView;
    TextView textView;
    int count = 0;
    private EditText name,age,sex,et_email,et_password;
    private Button sign_in,sign_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        imageView.setOnTouchListener(new OnSwipeTouchListener(LoginActivity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });

        et_email = findViewById(R.id.in_email);
        et_password = findViewById(R.id.in_pswd);

        sign_in = findViewById(R.id.btn_signin);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = et_email.getText().toString();
                String Pw = et_password.getText().toString();
                LoginAsync login = new LoginAsync();
                login.execute(Email,Pw);

            }
        });

        sign_up = findViewById(R.id.btn_signup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(signup);
            }
        });

        TextView no_login = findViewById(R.id.tv_NoLogin);
        no_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nologin = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(nologin);
            }
        });
    }

    public class LoginAsync extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(LoginActivity.this, "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loading.dismiss();
            //Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

            if (result == null){

                //mTextViewResult.setText(errorString);
            }
            else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("User_Info");

                    JSONObject item = jsonArray.getJSONObject(0);
                    String chk = item.getString(TAG_CHK);
                    String name = item.getString(TAG_NAME);
                    String age = item.getString(TAG_AGE);
                    String sex = item.getString(TAG_SEX);
                    String email = item.getString(TAG_EMAIL);
                    if(chk.equals("1")){
                        //success to login
                        HashMap<String,String> hashMap = new HashMap<>();

                        hashMap.put(TAG_NAME,name);
                        hashMap.put(TAG_AGE,age);
                        hashMap.put(TAG_SEX,sex);
                        hashMap.put(TAG_EMAIL,email);
                        mArrayList.add(hashMap);

                        SaveLoginData(mArrayList);
                        Intent profile = new Intent(LoginActivity.this,ProfileActivity.class);
                        startActivity(profile);
                    }else{
                        //fail to login
                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                        et_email.setText(null);
                        et_password.setText(null);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "showResult : ", e);
                }
            }
        }
        public void SaveLoginData(ArrayList<HashMap<String, String>> mArrayList) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(mArrayList);
            editor.putString("UserInfo", json);
            editor.commit();
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                String Email = (String) params[0];
                String Pw = (String) params[1];

                String link = pub_ip+"login.php";
                //String data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
                //data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");
                String data = "Email="+Email+"&Pw="+Pw;

                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();
                int responseStatusCode = conn.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream =conn.getInputStream();
                }
                else{
                    inputStream = conn.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
    }
}
