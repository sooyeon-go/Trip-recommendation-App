package com.example.ssadola;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_FESTIVAL = 1;

    public DBHelper(Context context) {
        super(context, "festival", null, DATABASE_FESTIVAL);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String festivalSQL = "create table tb_fetival" + "(_id integer primary key autoincrement, " + "title, " + "content)";
        db.execSQL(festivalSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == DATABASE_FESTIVAL){
            db.execSQL("drop table tb_fetival");
            onCreate(db);
        }
    }
}