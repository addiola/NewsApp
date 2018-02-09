package com.example.android.newsapp;

import android.support.v4.content.AsyncTaskLoader;
//import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Addi_ola on 17/01/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {


    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructor.
     *
     * @param context of the activity
     */

    public NewsLoader(Context context, String urls) {
        super(context);
        mUrl = urls;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        return QueryUtils.fetchNewsData(mUrl);

    }
}

