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
 * Created by Addi_ola on 07/01/2018.
 */

public final class QueryUtils {


    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final int READ_TIMEOUT = 10000;

    private static final int CONNECT_TIMEOUT = 15000;

    private static final int RESPONSE_CODE_OK = 200;

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<News> fetchNewsData(String requestUrl) {
//create URL object
        URL url = createUrl(requestUrl);

//perform HTTP request to URL created and receive a JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making HTTP request", e);
        }

//Extract revelant item (book title and author name from JSON response and create a lis of {@link BookItem)s
        List<News> newsList = extractFeaturesFromJson(jsonResponse);

//return the list
        return newsList;

    }

    /**
     * Return a new Url object from the given String
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
     * Make http request
     */

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT /* millisecnds */);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT /*milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == RESPONSE_CODE_OK) {
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
     * Return a list of  objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<News> extractFeaturesFromJson(String newsJson) {
// If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJson)) {
            return null;
        }

// Create an empty ArrayList
        List<News> newsList = new ArrayList<>();

//        // Try to parse the JSON response string. If there's a problem with the way the JSON
//        // is formatted, a JSONException exception object will be thrown.
//        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsJson);

            JSONObject responseJson = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "items",
            // which represents a list of books.
            JSONArray resultsArray = responseJson.getJSONArray("results");

            // For each item/book in the Array,
            for (int i = 0; i < resultsArray.length(); i++) {


                JSONObject currentNewsData = resultsArray.getJSONObject(i);

                //Extract Url String object for that book
                String webUrl = currentNewsData.getString("webUrl");

                String headline = "";
                String imgUrl = "";
                String authorName = "";


                if (currentNewsData.has("fields")) {
                    //create Json Object
                    JSONObject fields = currentNewsData.getJSONObject("fields");
                    if (fields.has("headline")) {
                        headline = fields.getString("headline");
                    }
                    if (fields.has("thumbnail")) {
                        imgUrl = fields.getString("thumbnail");
                    }
                }


                if (currentNewsData.has("tags")){
                    JSONArray tagsArray = currentNewsData.getJSONArray("tags");

                    String[] authors = new String[tagsArray.length()];



                    for(int j= 0; j< tagsArray.length(); j++) {
                        JSONObject currentTag = tagsArray.getJSONObject(j);

                        authors[j] = currentTag.getString("webTitle");

                        authorName +=  authors[j] + " , ";


                    }
                }


                // Create a new Book object
                News newsData = new News(webUrl, headline, imgUrl, authorName);

                // Add the new book to array list
                newsList.add(newsData);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

        // Return the list
        return newsList;
    }

}