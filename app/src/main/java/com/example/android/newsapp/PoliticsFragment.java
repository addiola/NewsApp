package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.LoaderManager;
import android.support.v4.app.LoaderManager;
//import android.content.Loader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Addi_ola on 21/01/2018.
 */

public class PoliticsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {


    /**
     * URL for data data from the USGS dataset
     */
    private static final String REQUEST_URL =
            "http://content.guardianapis.com/search?section=politics&show-fields=thumbnail%2Cheadline&api-key=81765130-7eed-41a9-956c-57d4fcb5147b";

    /**
     * Constant value for the loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    /* Adapter for the list*/
    private NewsAdapter mAdapter;

    private View rootView;
    /**TextView that is Displayed when the list is empty*/
    private TextView mEmptyStateTextView;

    /** more tabs stuff */
    public static BusinessFragment newInstance() {
        Bundle args = new Bundle();

        BusinessFragment fragment = new BusinessFragment();
        fragment.setArguments(args);
        return fragment;
    }
    /** needed for tabs?! */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_activity, container, false);

        ListView newsListView = rootView.findViewById(R.id.list);
        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);

        //Creat a new adapter that takes an empty list of news items
        mAdapter = new NewsAdapter(getActivity() , new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        //Get a reference to the ConnectivityManager to check the state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //IF there is network connection then fetch data
        if(networkInfo != null && networkInfo.isConnected()){
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null,this);


            // Set an item click listener on the ListView, which sends an intent to a web browser
            // to open a website with more information about the selected earthquake.
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current earthquake that was clicked on
                    News currentNews = mAdapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri newsUri = Uri.parse(currentNews.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });
        }else{
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = rootView.findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet);


        }

        return rootView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle){
        return new NewsLoader (getActivity(), REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsData){
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = rootView.findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display string when no data is found
        //mEmptyStateTextView.setText(R.string.empty_view);

        // Clear the adapter of previous  data
        mAdapter.clear();

        if(newsData != null && !newsData.isEmpty()){
            mAdapter.addAll(newsData);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

}




