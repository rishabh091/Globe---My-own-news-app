package com.example.newsapp;

import android.graphics.Bitmap;

public class News {
    String title;
    Bitmap image;
    String description;
    String source;
    String url;

    News(String t, Bitmap i,String d,String s,String u)
    {
        title=t;
        image=i;
        description=d;
        source=s;
        url=u;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }
}
