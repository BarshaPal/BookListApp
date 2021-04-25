package com.example.booklistapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;


public class BooksLoader extends AsyncTaskLoader<List<Books>> {

    /** Tag for log messages */
    private static final String LOG_TAG = BooksLoader.class.getName();

    /** Query URL */
    private String mUrl;


    public BooksLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.e("Start Loder","Start Loder called");
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Books> loadInBackground() {
        if (mUrl == null) {
            Log.e("Background Loder","Null Background Loder called");
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Books> earthquakes = Utils.extractEarthquakes(mUrl);
        return earthquakes;
    }
}