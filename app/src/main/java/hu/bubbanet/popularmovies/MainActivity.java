/**
 * Created by Anita Goldpergel (anita.goldpergel@gmail.com) on 2018.05.12.
 *
 * * * MAINACTIVITY Class
 *
 */

package hu.bubbanet.popularmovies;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.LoaderManager;

import android.net.NetworkInfo;
import android.net.Uri;
import android.net.ConnectivityManager;

import android.content.Intent;
import android.content.Context;
import android.content.Loader;

import android.widget.ProgressBar;
import android.widget.TextView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import hu.bubbanet.popularmovies.utils.MovieLoader;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String API_KEY = BuildConfig.API_KEY;
    private String MOVIE_DB_REQUEST_URL;
    private static final int ID_MOVIE_LOADER = 13;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Movie> movieList = new ArrayList<>();
    private List<Movie> savedMovieList = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private TextView emptyTextView;
    private String ORDER_BUY_POPULAR = "popular";
    private String ORDER_BUY_TOP_RATED = "top_rated";
    private String orderBy = ORDER_BUY_POPULAR;
    static final String MOVIE_DETAILS = "movie_details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MOVIE_DB_REQUEST_URL = "https://api.themoviedb.org/3/movie";

        // find view by ids
        recyclerView = findViewById(R.id.rv_movies);
        progressBar = findViewById(R.id.progress_bar);
        emptyTextView = findViewById(R.id.tv_empty_state);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        MovieAdapter.OnItemClickListener listener = new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Intent detailsIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailsIntent.putExtra(MOVIE_DETAILS, Parcels.wrap(movie));
                startActivity(detailsIntent);
            }
        };

        movieAdapter = new MovieAdapter(this, movieList, listener);
        recyclerView.setAdapter(movieAdapter);

        // Get a reference to the ConnectivityManager
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connManager != null;
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        // get the data if connected
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(ID_MOVIE_LOADER, null, this);
        } else {
            View progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);

            emptyTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {

        //builds the URI according to the query parameters below
        Uri baseUri = Uri.parse(MOVIE_DB_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(orderBy);
        uriBuilder.appendQueryParameter("api_key", API_KEY);

        return new MovieLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        //hides the progress bar circle
        progressBar.setVisibility(View.GONE);

        // Clear the adapter of previous movie data
        movieAdapter.setMovieList(null);

        // If there is a valid list of movieList, then add them to the adapter's data set.
        if (movies != null && !movies.isEmpty()) {
            emptyTextView.setVisibility(View.GONE);
            movieAdapter.setMovieList(movies);
            movieAdapter.notifyDataSetChanged();
            savedMovieList = new ArrayList<>(movies);

        } else {
            // Set empty state text to display "No movieList found."
            emptyTextView.setText(R.string.no_movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        movieAdapter.setMovieList(null);
    }

    //Creates menu on the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies, menu);
        //Return true so that the menu is displayed in the Toolbar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //Checks the setting choosen by the user, and refreshes layout if necessary

        if (id == R.id.most_popular) {
            if (!orderBy.equals(ORDER_BUY_POPULAR)) {
                orderBy = ORDER_BUY_POPULAR;
                getLoaderManager().restartLoader(ID_MOVIE_LOADER, null, this);
                return true;
            }
        }
        if (id == R.id.highest_rated) {
            if (!orderBy.equals(ORDER_BUY_TOP_RATED)) {
                orderBy = ORDER_BUY_TOP_RATED;
                getLoaderManager().restartLoader(ID_MOVIE_LOADER, null, this);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
