package com.example.ssadola;

import android.graphics.drawable.Drawable;

public class MyItem {

    private Drawable icon;
    private String name;
    private String contents;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getCalssfi() {
        return name;
    }

    public void setClassfi(String name) {
        this.name = name;
    }

    public String getCourse() {
        return contents;
    }

    public void setCourse(String contents) {
        this.contents = contents;
    }

}