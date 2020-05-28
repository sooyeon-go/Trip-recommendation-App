package com.example.ssadola;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;

import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarkFragment extends Fragment implements ScreenShotable {
    ArrayList<HashMap<String, String>> arrayList;
    ArrayList<HashMap<String,String>> BookmarkList;
    TextView test;
    String mJsonString;
    private RecyclerView rv;
    protected int res;
    private View containerView;

    static String pub_ip = "http://15.165.95.187/";
    StringBuilder sb;
    private static final String TAG_EMAIL="u_email";
    private static final String TAG_NAME = "u_name";
    private static final String TAG_THEME = "theme";
    private static final String TAG_ADDR = "addr";
    private static final String TAG_WOKR = "work_nm";
    private static final String TAG_COUNT = "count";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    public static BookmarkFragment newInstance(int resId) {
        BookmarkFragment bookmarkfragment = new BookmarkFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        bookmarkfragment.setArguments(bundle);
        return bookmarkfragment;
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
        View view = inflater.inflate(R.layout.fragment_bookmark,container,false);
        BookmarkList = new ArrayList<>();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = view.findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLinearLayoutManager);
        arrayList = GetLoginData();

        if(arrayList == null){
            //Toast.makeText(BookmarkActivity.this,"로그인 먼저 해주세요",Toast.LENGTH_LONG).show();
            Intent login = new Intent(getActivity(),LoginActivity.class);
            startActivity(login);
        }else{
            //db에서 즐찾한 여행지 정보들 가져와서 보여주기
            //유저정보를 인자로 넘겨주고 디비에서 셀렉해오기
            HashMap<String, String> LoginhashMap = arrayList.get(0);
            String mu_email = LoginhashMap.get(TAG_EMAIL);
            //Toast.makeText(BookmarkActivity.this,mu_email,Toast.LENGTH_LONG).show();
            //String mu_name = LoginhashMap.get(TAG_NAME);
            GetBookmark getbm = new GetBookmark();
            getbm.execute(mu_email);
        }

        return view;
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
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    class GetBookmark extends AsyncTask<String, Void, String> {

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
            if(result == null){
            }else{
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("BookmarkTheme");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        String i_email = item.getString(TAG_EMAIL);
                        //System.out.println(i+" "+i_email);
                        String i_theme = item.getString(TAG_THEME);
                        //System.out.println(i+" "+i_theme);
                        String i_addr = item.getString(TAG_ADDR);
                        //System.out.println(i+" "+i_addr);
                        String i_worknm = item.getString(TAG_WOKR);
                        //System.out.println(i+" "+i_worknm);

                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put(TAG_EMAIL,i_email);
                        hashMap.put(TAG_THEME,i_theme);
                        hashMap.put(TAG_ADDR,i_addr);
                        hashMap.put(TAG_WOKR,i_worknm);
                        hashMap.put(TAG_COUNT,Integer.toString(i+1));

                        BookmarkList.add(hashMap);
                    }
                    RecyclerAdapter adapter = new RecyclerAdapter(getActivity(),BookmarkList);
                    Log.e("onCreate[BookmarkList]", "" + BookmarkList.size());
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.d("BookmarkActivity", "showResult : ", e);
                }
            }
        }
        @Override
        protected String doInBackground(String... params) {
            //String name = param[0];
            final String link = pub_ip + "getBookmarkTheme.php";
            String email = params[0];
            final String data = "u_email=" + email;
            try {

                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();
                wr.close();

                int responseStatusCode = conn.getResponseCode();
                //Log.d(TAG, "response code - " + responseStatusCode);
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream =conn.getInputStream();
                }
                else {
                    inputStream = conn.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


                sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return new String("Exception: " + e.getMessage());
            }
        }
    }
}
