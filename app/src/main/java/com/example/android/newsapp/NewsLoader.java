package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of news by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String WebUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param webUrl to load data from
     */
    public NewsLoader(Context context, String webUrl) {
        super(context);
        WebUrl = webUrl;
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
        if (WebUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news stories.
        List<News> newsstories = QueryUtils.fetchNewsData(WebUrl);
        return newsstories;
    }
}
