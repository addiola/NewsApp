package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Addi_ola on 17/01/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    ImageView bmImage;
    Context context;

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        News currentNewsData = getItem(position);

        //Textview
        TextView headlineText = convertView.findViewById(R.id.headline);
        //set Text
        headlineText.setText(currentNewsData.getHeadLine());

        TextView authorText = convertView.findViewById(R.id.author_name);
        authorText.setText(currentNewsData.getAuthorName());

        bmImage = convertView.findViewById(R.id.news_thumbnail);
        if (currentNewsData.getImageUrl().isEmpty()) {

            bmImage.setImageResource(R.drawable.placeholder);

        } else {


            Picasso.with(context)
                    .load(currentNewsData.getImageUrl())
                    .placeholder(R.drawable.placeholder)   // optional
                    .error(R.drawable.error)      // optional
                    .into(bmImage);

        }
        //return view
        return convertView;
    }


}















