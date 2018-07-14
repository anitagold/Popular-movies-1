/**
 * Created by Anita Goldpergel (anita.goldpergel@gmail.com) on 2018.05.18.
 *
 * * * MOVIELOADER Class
 *
 */

package hu.bubbanet.popularmovies.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import hu.bubbanet.popularmovies.Movie;


public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String LOG_TAG = MovieLoader.class.getName();
    private String urlStr;

    public MovieLoader(Context context, String urlStr) {
        super(context);
        this.urlStr = urlStr;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (urlStr == null) {
            return null;
        }

        return QueryMovies.getMovieData(urlStr);
    }
}