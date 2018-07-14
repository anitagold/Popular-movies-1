/**
 * Created by Anita Goldpergel (anita.goldpergel@gmail.com) on 2018.04.25.
 *
 * * * JSONPARSER Class
 *
 */

package hu.bubbanet.popularmovies.utils;

import hu.bubbanet.popularmovies.Movie;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    private static final String ID = "id";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String RESULTS = "results";

    // parser method gets the list of movies from the JSON string
    // Returns null, when the sting is empty
    // It throws JSONException when the string format is not good.
    public static List<Movie> parser(String jsonStr) {

        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }

        List<Movie> movieList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray movieArray = jsonObject.getJSONArray(RESULTS);
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movieJson = movieArray.getJSONObject(i);

                int id = 0;
                String originalTitle = "";
                String posterPath = "";
                String backdropPath = "";
                String releaseDate = "";
                double voteAverage = 0;
                String overview = "";

                if (movieJson.has(ID)) { id = movieJson.optInt(ID); }
                if (movieJson.has(ORIGINAL_TITLE)) { originalTitle = movieJson.optString(ORIGINAL_TITLE); }
                if (movieJson.has(POSTER_PATH)) { posterPath = movieJson.optString(POSTER_PATH); }
                if (movieJson.has(BACKDROP_PATH)) { backdropPath = movieJson.optString(BACKDROP_PATH); }
                if (movieJson.has(RELEASE_DATE)) { releaseDate = movieJson.optString(RELEASE_DATE); }
                if (movieJson.has(VOTE_AVERAGE)) { voteAverage = movieJson.optDouble(VOTE_AVERAGE); }
                if (movieJson.has(OVERVIEW)) { overview = movieJson.optString(OVERVIEW); }

                Movie actualMovie = new Movie(id, originalTitle, posterPath, backdropPath, releaseDate, voteAverage, overview);

                movieList.add(actualMovie);
            }

        } catch (JSONException e) {
            Log.e("JSONParser ", "Parsing error: ", e);
        }
        return movieList;
    }
}

