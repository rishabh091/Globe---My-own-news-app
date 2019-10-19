package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    NewsAdapter(Context context, ArrayList<News> arr)
    {
        super(context,0,arr);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView=convertView;
        if(listView==null)
        {
            listView=LayoutInflater.from(getContext()).inflate(R.layout.list_layout,parent,false);
        }
        final News newsObj=getItem(position);

        LinearLayout linearLayout=(LinearLayout) listView.findViewById(R.id.linearlayoutId);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIntent(newsObj.getUrl());
            }
        });

        TextView title=(TextView) listView.findViewById(R.id.headlinesId);
        title.setText(newsObj.getTitle());

        ImageView image=(ImageView) listView.findViewById(R.id.imageId);
        image.setImageBitmap(newsObj.getImage());

        TextView description=(TextView) listView.findViewById(R.id.descriptionId);
        description.setText(newsObj.getDescription());

        TextView source=(TextView) listView.findViewById(R.id.sourceId);
        source.setText("Source : "+newsObj.getSource());

        return listView;
    }

    //intent
    void setIntent(String url)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        getContext().startActivity(intent);
    }
}
