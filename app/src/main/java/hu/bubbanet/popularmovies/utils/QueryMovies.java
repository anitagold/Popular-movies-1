/**
 * Created by Anita Goldpergel (anita.goldpergel@gmail.com) on 2018.05.18.
 *
 * * * QUERYMOVIES Class
 *
 */
package hu.bubbanet.popularmovies.utils;

import java.nio.charset.Charset;
import java.util.List;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

import hu.bubbanet.popularmovies.Movie;

//Query the movie database and create a list of movies
public class QueryMovies {

    private static final String LOG_TAG = QueryMovies.class.getSimpleName();

    private QueryMovies() {
    }

    // create URL object from the given stringUrl string
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "URL creation error: ", exception);
        }
        return url;
    }

    private static String readFromStream(InputStream inputStr) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStr != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStr, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    // HTTP request with the URL and return a JSON String
    private static String httpRequest(URL url) throws IOException {
        String returnJson = "";

        //return early if url is null
        if (url == null) {
            return returnJson;
        }

        HttpURLConnection urlConnect = null;
        InputStream inputStream = null;
        try {
            urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setRequestMethod("GET");
            urlConnect.setReadTimeout(10000);
            urlConnect.setConnectTimeout(15000);
            urlConnect.connect();

            if (urlConnect.getResponseCode() == 200) {
                inputStream = urlConnect.getInputStream();
                returnJson = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "HTTP error response code: " + urlConnect.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retreiving the movie JSON results.", e);

        } finally {
            if (urlConnect != null) {
                urlConnect.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return returnJson;
    }

    public static List<Movie> getMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = httpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Movie> movies = JSONParser.parser(jsonResponse);

        return movies;
    }


 }
