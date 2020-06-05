package com.example.ssadola;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

public class CardItem {
    private String mTextResource;
    private String mTitleResource;
    private String mImgUrl;
    private Bitmap mBm;


    public CardItem(String title) {
        mTitleResource = title;
    }

    public CardItem(String title, String text) {
        mTitleResource = title;
        mTextResource = text;
    }

    public CardItem(String title, Bitmap bm) {
        mTitleResource = title;
        mBm = bm;
    }

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public String getUrl(){
        return mImgUrl;
    }

    public Bitmap getBitmap(){
        return mBm;
    }

}
