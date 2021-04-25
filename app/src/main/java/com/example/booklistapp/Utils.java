package com.example.booklistapp;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;

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

public class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static List<Books> extractEarthquakes(String Url) {
        Log.e("List Loder","List Loder called");
        // Create an empty ArrayList that we can start adding earthquakes to
        URL url = createurl(Url);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<Books> earthquake = extractFeatureFromJson(jsonResponse);

        return earthquake;
    }

    private static List<Books> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<Books> earthquakes = new ArrayList<>();
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            // Extract the JSONArray associated with the key called "items",
            // which represents a list of items (or books).
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");

            // For each book in the bookArray, create an {@link Book} object
            for (int i = 0; i < bookArray.length(); i++) {

                // Get a single book at position i within the list of books
                JSONObject currentBook = bookArray.getJSONObject(i);

                // For a given book, extract the JSONObject associated with the
                // key called "volumeInfo"
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                String subtitle="";
                String page="";
                String date="";
                String buy="";
                // Extract the value for the key called "title"
                String title = volumeInfo.getString("title");
                 if(volumeInfo.has("subtitle"))
                 {
                      subtitle = volumeInfo.getString("subtitle");
                 }
                 else
                 {
                     subtitle="No subtitle";
                 }
                if(volumeInfo.has("publishedDate"))
                {
                     date=volumeInfo.getString("publishedDate");
                }
                if(volumeInfo.has("pageCount"))
                {
                    page+=volumeInfo.getString("pageCount");
                }
                else
                {
                    page+="Not specified";
                }
                JSONObject buyinfo = currentBook.getJSONObject("saleInfo");
                if(buyinfo.has("buyLink"))
                {
                    buy=buyinfo.getString("buyLink");
                }
                JSONArray authorsArray = volumeInfo.getJSONArray("authors");

                // declare a string builder to store authors names
                StringBuilder authorsName = new StringBuilder();

                // for each author in authorsArray, extract it and add it to a string builder
                for(int j = 0; j < authorsArray.length(); j++){
                    authorsName.append(authorsArray.getString(j));
                }

                // store authors names into a string
                String authors = authorsName.toString();

                Books earthquake = new Books(title,subtitle,authors,date,page,buy);
                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {
            e.printStackTrace();Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        // Return the list of earthquakes
        return earthquakes;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

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


    private static URL createurl(String stringurl) {
        URL url=null;
        try{
            url=new URL(stringurl);
        }catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Error with creating URL ", e);

        }
        return url;
    }

}