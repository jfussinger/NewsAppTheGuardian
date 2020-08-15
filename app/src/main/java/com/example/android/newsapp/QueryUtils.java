package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving book listing data from the GUARDIAN.
 */
public final class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the GUARDIAN dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}
        List<News> newsstories = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News}
        return newsstories;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<News> extractFeatureFromJson(String responseJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(responseJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding newsstories to
        List<News> newsstories = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject jsonObject = new JSONObject(responseJSON);

            JSONObject response = jsonObject.getJSONObject("response");

            JSONArray resultsArray = response.getJSONArray("results");

            // For each news in the newsArray, create an {@link News} object
            for (int i = 0; i < resultsArray.length(); i++) {

                // Get a single newsstory at position i within the list of newsstories
                JSONObject currentNews = resultsArray.getJSONObject(i);

                String sectionName = "";
                String webPublicationDate = "";
                String webTitle = "";


                if(currentNews.has("sectionName")) {
                    // Extract the value for the key called "sectionName"
                    sectionName = currentNews.getString("sectionName");

                }
                else {
                    sectionName = "No sectionName!";
                }

                if(currentNews.has("webPublicationDate")) {
                    // Extract the value for the key called "webPublicationDate"
                    webPublicationDate = currentNews.getString("webPublicationDate");

                }
                else {
                    webPublicationDate = "No webPublicationDate!";
                }


                if(currentNews.has("webTitle")) {
                    // Extract the value for the key called "webTitle"
                    webTitle = currentNews.getString("webTitle");

                }
                else {
                    webTitle = "No webTitle!";
                }

                // Extract the JSONArray associated with the key called "tags",
                // which represents a list of features (or newsstories).
                JSONArray tags = currentNews.getJSONArray("tags");

                for (int j =0; j < tags.length(); j++) {
                    // Get a single news at position i within the list of news
                    JSONObject currentTag = tags.getJSONObject(j);

                    String contributor = "";
                    String webUrl = "";

                    if(currentTag.has("webTitle")) {
                        // Extract the value for the key called "webTitle"
                        contributor = currentTag.getString("webTitle");

                    }
                    else {
                        contributor = "No webTitle!";
                    }

                    if(currentTag.has("webUrl")) {
                        // Extract the value for the key called "webUrl"
                        webUrl = currentTag.getString("webUrl");

                    }
                    else {
                        webUrl = "No webUrl!";
                    }

                    // Create a new {@link News} object with the sectionName, webPublicationDate, webTitle, contributor, webUrl
                    // from the JSON response.
                    News news = new News(sectionName, webPublicationDate, webTitle, contributor, webUrl);

                    // Add the new {@link News} to the list of news stories.
                    newsstories.add(news);
                }
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }

        // Return the list of news
        return newsstories;
    }

}