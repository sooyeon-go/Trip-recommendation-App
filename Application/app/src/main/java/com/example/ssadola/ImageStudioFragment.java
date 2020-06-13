/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ssadola;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
//com.google.android.apps.maps
//com.google.android.apps.maps.MapsActivity
/**
 * This fragment will populate the children of the ViewPager from {@link ImageStudioActivity}.
 */
public class ImageStudioFragment extends Fragment implements ImageWorker.OnImageLoadedListener {
    private static final String IMAGE_DATA_EXTRA = "extra_image_data";
    private String mImageUrl;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private TextView mScene,mLocation,mAddress;
    private ImageButton mBookmark;
    String i_scene, i_location,i_address,i_title;
    StringBuilder sb;
    ArrayList<HashMap<String, String>> arrayList;
    static String pub_ip = "http://15.165.95.187/";
    /**
     * Factory method to generate a new instance of the fragment given an image number.
     *
     * @param imageUrl The image url to load
     * @return A new instance of ImageDetailFragment with imageNum extras
     */
    static ImageStudioFragment newInstance(String imageUrl) {
        final ImageStudioFragment f = new ImageStudioFragment();

        final Bundle args = new Bundle();
        args.putString(IMAGE_DATA_EXTRA, imageUrl);
        f.setArguments(args);

        return f;
    }

    /**
     * Empty constructor as per the Fragment documentation
     */
    public ImageStudioFragment() {
    }

    /**
     * Populate image using a url from extras, use the convenience factory method
     * {@link ImageStudioFragment#newInstance(String)} to create this fragment.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString(IMAGE_DATA_EXTRA) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate and locate the main ImageView
        final View v = inflater.inflate(R.layout.fragment_image_studio, container, false);
        mImageView = v.findViewById(R.id.imageView);
        mProgressBar = v.findViewById(R.id.progressbar);
        mScene = v.findViewById(R.id.tv_studio_scene);
        mAddress = v.findViewById(R.id.tv_studio_address);
        mLocation = v.findViewById(R.id.tv_studio_location);
        mBookmark = v.findViewById(R.id.ic_bookmark_selector);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();

        // Use the parent activity to load the image asynchronously into the ImageView (so a single
        // cache can be used over all pages in the ViewPager
        if (activity instanceof ImageStudioActivity) {
            ImageFetcher mImageFetcher = ((ImageStudioActivity) getActivity()).getImageFetcher();
            mImageFetcher.loadImage(mImageUrl, mImageView, this);
            //Toast.makeText(getActivity(),mImageUrl,Toast.LENGTH_LONG).show();
            Studio_info info = new Studio_info();
            info.execute(mImageUrl);
        }

        // Pass clicks on the ImageView to the parent activity to handle
        if (activity instanceof View.OnClickListener) {
            mImageView.setOnClickListener((View.OnClickListener) getActivity());
            mAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("http://maps.google.com/maps?f=d&daddr="+mAddress.getText()+"&hl=ko");
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                    intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            });
            mBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //로그인 정보 검사
                    arrayList = GetLoginData();

                    if(arrayList == null){
                        Toast.makeText(v.getContext(),"로그인 먼저 해주세요",Toast.LENGTH_LONG).show();
                        Intent login = new Intent(v.getContext(),LoginActivity.class);
                        startActivity(login);
                        //v.getContext().finish();
                    }else {
                        if (mBookmark.isSelected()) {

                            mBookmark.setSelected(false);
                            mBookmark.setPressed(false);
                            mBookmark.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_default_24dp));
                            //bookmark_StudioDelete.php 실행
                        } else {
                            mBookmark.setSelected(true);
                            mBookmark.setPressed(true);
                            mBookmark.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_24dp));
                            //bookmark_StudioInsert.php 실행
                            HashMap<String,String> hashMap = arrayList.get(0);
                            String u_email = hashMap.get("u_email");

                            Bookmark_insert insert = new Bookmark_insert();
                            insert.execute(u_email,i_title,i_scene,i_location,i_address,mImageUrl);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImageView != null) {
            // Cancel any pending image work
            ImageWorker.cancelWork(mImageView);
            mImageView.setImageDrawable(null);
        }
    }
    public ArrayList<HashMap<String, String>> GetLoginData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("UserInfo", "EMPTY");
        if(json.equals("EMPTY")){
            //Toast.makeText(BookmarkActivity.this,"로그인 먼저 해주세요",Toast.LENGTH_LONG).show();
            return null;
        }
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
    @Override
    public void onImageLoaded(boolean success) {
        // Set loading spinner to gone once image has loaded. Cloud also show
        // an error view here if needed.
        mProgressBar.setVisibility(View.GONE);
    }
    class Studio_info extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //loading = ProgressDialog.show(TitleStudioActivity.this, "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //loading.dismiss();
            System.out.println("Result : " + result);
            if(result!=null){
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("Studio_Info");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        i_title = item.getString("work_title");
                        i_scene = item.getString("scene");
                        i_location = item.getString("location");
                        i_address = item.getString("address");

                        /*HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("work_title",i_scene;
                        hashMap.put("img",i_location);
                        arrayList.add(hashMap);*/
                    }
                    mScene.setText(i_scene);
                    mLocation.setText(i_location);
                    mAddress.setText(i_address);
                } catch (JSONException e) {
                    Log.e("ImageStudioFragment", "showResult : ", e);

                }
            }else{
                Log.e("ImageStudioFragment","result is null");
            }
        }
        @Override
        protected String doInBackground(String... params) {
            final String link = pub_ip + "getStudio_Info.php";
            String imgUrl = params[0];
            try {
                URL url = new URL(link);
                String data = URLEncoder.encode("img", "UTF-8") + "=" + URLEncoder.encode(imgUrl, "UTF-8");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                conn.setDoOutput(true);
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
                reader.close();
                System.out.println(sb.toString());
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return new String("Exception: " + e.getMessage());
            }
        }
    }
    class Bookmark_insert extends AsyncTask<String, Void, String> {
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //loading.dismiss();
            System.out.println("Result : " + result);
            if(result!=null){
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            }else{
                Log.e("ImageStudioFragment","result is null");
            }
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                //u_email,i_title,i_scene,i_location,i_address,mImageUrl
                String u_email = (String) params[0];
                String title = (String) params[1];
                String scene = params[2];
                String location = params[3];
                String address = params[4];
                String image = params[5];

                String link = pub_ip+"Bookmark_insert.php";
                //String data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
                //data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");
                String data = "u_email="+u_email+"&title="+URLEncoder.encode(title,"UTF-8")
                        + "&scene="+URLEncoder.encode(scene,"UTF-8") + "&location="+URLEncoder.encode(location,"UTF-8")
                        +"&address="+URLEncoder.encode(address,"UTF-8")+"&image="+URLEncoder.encode(image,"UTF-8");

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
                Log.d("Bookmark_insert", "response code - " + responseStatusCode);
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
                return sb.toString();
            } catch (Exception e) {
                return e.toString();
            }
        }
    }
}
