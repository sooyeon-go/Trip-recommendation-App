package com.example.ssadola;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class LoginFragment extends Fragment implements ScreenShotable {
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
    public static LoginFragment newInstance(int resId) {
        LoginFragment loginfragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        loginfragment.setArguments(bundle);
        return loginfragment;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        imageView = rootView.findViewById(R.id.imageView);
        textView = rootView.findViewById(R.id.textView);
        imageView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
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

        et_email = rootView.findViewById(R.id.in_email);
        et_password = rootView.findViewById(R.id.in_pswd);

        sign_in = rootView.findViewById(R.id.btn_signin);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = et_email.getText().toString();
                String Pw = et_password.getText().toString();
                LoginAsync login = new LoginAsync();
                login.execute(Email,Pw);

            }
        });

        sign_up = rootView.findViewById(R.id.btn_signup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(getActivity(),SignUpActivity.class);
                startActivity(signup);
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

    public class LoginAsync extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
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
                        Intent profile = new Intent(getActivity(),ProfileActivity.class);
                        startActivity(profile);
                    }else{
                        //fail to login
                        Toast.makeText(getActivity(), "로그인 실패", Toast.LENGTH_LONG).show();
                        et_email.setText(null);
                        et_password.setText(null);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "showResult : ", e);
                }
            }
        }
        public void SaveLoginData(ArrayList<HashMap<String, String>> mArrayList) {
            SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(getContext());
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
