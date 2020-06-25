package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SignUpActivity extends AppCompatActivity {
    static String pub_ip = "http://15.165.95.187/";
    private EditText name;
    private EditText age;
    private EditText sex;
    private EditText email;
    private EditText password;
    private Button btn_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.up_name);
        age = findViewById(R.id.up_age);
        sex = findViewById(R.id.up_sex);
        email = findViewById(R.id.up_email);
        password = findViewById(R.id.up_pswd);
        btn_clear = findViewById(R.id.btn_signup_compelete);

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Age = age.getText().toString();
                String Sex = sex.getText().toString();
                String Email = email.getText().toString();
                String Pw = password.getText().toString();
                InsertAsync task = new InsertAsync();
                task.execute(Name,Age,Sex,Email,Pw);
            }
        });

    }
    private class InsertAsync extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(SignUpActivity.this, "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String Name = (String) params[0];
                String Age = (String) params[1];
                String Sex = (String) params[2];
                String Email = (String) params[3];
                String Pw = (String) params[4];

                String link = pub_ip+"signUP.php";
                String data = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                data += "&" + URLEncoder.encode("Age", "UTF-8") + "=" + URLEncoder.encode(Age, "UTF-8");
                data += "&" + URLEncoder.encode("Sex", "UTF-8") + "=" + URLEncoder.encode(Sex, "UTF-8");
                data += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
                data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");

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
