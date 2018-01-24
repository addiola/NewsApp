package com.example.android.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Addi_ola on 17/01/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    ImageView bmImage;
    public NewsAdapter(Context context, List<News> news) {
        super(context,0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        News currentNewsData = getItem(position);

        //Textview
        TextView headlineText = convertView.findViewById(R.id.headline);
        //set Text
        headlineText.setText(currentNewsData.getHeadLine());

        TextView linkText = convertView.findViewById(R.id.link_to_news_page);

        String url = currentNewsData.getUrl();

        //set Text
        linkText.setText(R.string.read_more);


        new DownloadImageTask().execute(currentNewsData.getImageUrl());

        bmImage = convertView.findViewById(R.id.news_thumbnail);

        //return view
        return convertView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


        public DownloadImageTask() {

        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}















