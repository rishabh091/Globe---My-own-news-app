package com.example.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {
    public
    String url;
    public NewsLoader(Context context,String u)
    {
        super(context);
        url=u;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {
        ArrayList<News> arrayList;
        try
        {
            URL urlObject=new URL(url);
            HttpURLConnection httpURLConnection=(HttpURLConnection) urlObject.openConnection();
            httpURLConnection.connect();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder=new StringBuilder();
            String str=bufferedReader.readLine();
            while(str!=null)
            {
                stringBuilder.append(str);
                str=bufferedReader.readLine();
            }

            arrayList=jsonParsing(stringBuilder.toString());
            return arrayList;
        }
        catch (Exception e)
        {
            Log.v("NewsLoader","Cant connect to Internet : "+e.toString());
            return null;
        }
    }

    public ArrayList jsonParsing(String jsonStr)
    {
        String urlToImage;
        ArrayList<News> news=new ArrayList<News>();
        if(jsonStr!=null)
        {
            try
            {
                JSONObject jsonObject=new JSONObject(jsonStr);
                JSONArray articles=jsonObject.getJSONArray("articles");
                for(int i=0;i<articles.length();i++)
                {
                    JSONObject newsNo=articles.getJSONObject(i);
                    String title=newsNo.getString("title");
                    urlToImage=newsNo.getString("urlToImage");
                    Bitmap image=getOnlineImage(urlToImage);
                    String description=newsNo.getString("description");
                    String url=newsNo.getString("url");
                    JSONObject source=newsNo.getJSONObject("source");
                    String name=source.getString("name");
                    news.add(new News(title,image,description,name,url));
                }
                return news;
            }
            catch (Exception e)
            {
                Log.v("NewsLoader","Cant convert to JSON : "+e.toString());
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public Bitmap getOnlineImage(String url)
    {
        try
        {
            URL urlObject=new URL(url);
            HttpURLConnection httpURLConnection=(HttpURLConnection) urlObject.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream input=httpURLConnection.getInputStream();
            Bitmap titleImage= BitmapFactory.decodeStream(input);
            return  titleImage;
        }
        catch (Exception e)
        {
            Log.v("NewsLoader","Cant get image : "+url);
            return null;
        }
    }
}
