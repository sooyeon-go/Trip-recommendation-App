package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.util.Log;


public class ReviewResult extends AppCompatActivity {
    private static String pub_ip = "http://15.165.95.187/";
    private Button button;
    private final String dbName = "review_db";
    private final String tableName = "reviewtable";
    private static final String TAG_HOTEL = "hotel";
    private static final String TAG_SIGHT ="sight";
    private static final String TAG_EAT ="eat";
    private static final String TAG_PLACE ="place";
    private static final String TAG_RATING ="rating";
    private static final String TAG_SPEC ="spec";
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_list);
        personList = new ArrayList<HashMap<String,String>>();
        button = (Button)findViewById(R.id.button);

        try{
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            Cursor c = ReadDB.rawQuery("SELECT * FROM " + tableName, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        String hotel = c.getString(c.getColumnIndex("hotel"));
                        String sight = c.getString(c.getColumnIndex("sight"));
                        String eat = c.getString(c.getColumnIndex("eat"));
                        String place = c.getString(c.getColumnIndex("place"));
                        String rating = c.getString(c.getColumnIndex("rating"));
                        String spec = c.getString(c.getColumnIndex("spec"));

                        HashMap<String,String> persons = new HashMap<String,String>();

                        persons.put(TAG_HOTEL,hotel);
                        persons.put(TAG_SIGHT,sight);
                        persons.put(TAG_EAT,eat);
                        persons.put(TAG_PLACE,place);
                        persons.put(TAG_RATING,rating);
                        persons.put(TAG_SPEC,spec);

                        personList.add(persons);

                    } while (c.moveToNext());
                }
            }
            ReadDB.close();
            adapter = new SimpleAdapter(
                    this, personList, R.layout.review_list_item,
                    new String[]{TAG_HOTEL, TAG_SIGHT, TAG_EAT, TAG_PLACE, TAG_RATING, TAG_SPEC},
                    new int[]{R.id.hotel, R.id.sight, R.id.eat, R.id.place, R.id.rating, R.id.spec}
            );
            list.setAdapter(adapter);
        }catch (SQLiteException se) {
                Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("", se.getMessage());
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            Intent intent=new Intent(ReviewResult.this,MainActivity.class);
            startActivity(intent);
        }});
    }
}
