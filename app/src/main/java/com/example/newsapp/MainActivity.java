package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    public static String url="https://newsapi.org/v2/top-headlines?";
    public static String country="country=in&";
    public static String apiKey="apiKey=ccbf3148d2794048ac2a307027d14d3d";

    public static String everythingURL="https://newsapi.org/v2/everything?";
    public String search="india";
    public static String link=url+country+apiKey;

    //public final String url="https://newsapi.org/v2/top-headlines?country=in&apiKey=ccbf3148d2794048ac2a307027d14d3d";
    NewsAdapter newsAdapter;
    ProgressBar progressBar;
    TextView emptyText;

    public LoaderManager loaderManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=(ProgressBar) findViewById(R.id.progressCircular);
        emptyText=(TextView) findViewById(R.id.nointernetId);

        ImageButton imageButton=(ImageButton) findViewById(R.id.buttonId);

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean b=connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnected();
        if(b==false)
        {
            emptyText.setText("OOPS!! \n We cant connect to Internet");
            progressBar.setVisibility(View.INVISIBLE);
        }
        else
        {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
                    startActivity(intent);
                    search=SearchActivity.getSearch();
                    Log.v("MainActivity","Link is : "+link);
                    //newsAdapter.clear();
                    if(newsAdapter.isEmpty())
                    {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    loaderManager=getSupportLoaderManager();
                    callLoaderManager(loaderManager);
                }
            });
            loaderManager=getSupportLoaderManager();
            callLoaderManager(loaderManager);
        }
    }

    public void callLoaderManager(LoaderManager loaderManager)
    {
        loaderManager.restartLoader(0,null,this);
    }
    public void getAdapter(ArrayList news)
    {
        newsAdapter=new NewsAdapter(this,news);
        ListView listView=(ListView) findViewById(R.id.listId);
        listView.setEmptyView(emptyText);
        listView.setAdapter(newsAdapter);
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        NewsLoader newsLoader=new NewsLoader(this,link);
        if(newsLoader==null)
        {
            emptyText.setText("Nothing to display");
            return null;
        }
        else
        {
            return newsLoader;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> news) {
        if(news!=null)
        {
            progressBar.setVisibility(View.INVISIBLE);
            getAdapter(news);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        newsAdapter.clear();
    }
}
