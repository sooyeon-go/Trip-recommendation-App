package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SpringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring);

        TextView titleView = (TextView) findViewById(R.id.tv_springfest_title);
        TextView contentView = (TextView) findViewById(R.id.tv_springfest_content);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select title, content from tb_festival" +
                "             order by _id desc limit 1", null);

        while (cursor .moveToNext()){
            titleView.setText(cursor.getString(0));
            contentView.setText(cursor.getString(1));

        }
        db.close();
    }
}
