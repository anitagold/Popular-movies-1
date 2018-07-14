/**
 * Created by Anita Goldpergel (anita.goldpergel@gmail.com) on 2018.05.18.
 *
 * * * DETAILACTIVITY Class
 *
 */

package hu.bubbanet.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TextView originalTitleView;
    TextView releaseDateView;
    ImageView backdropView;
    TextView voteAverageView;
    TextView overviewView;

    static final String MOVIE_DETAILS = "movie_details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        originalTitleView = findViewById(R.id.tv_original_title);
        releaseDateView = findViewById(R.id.tv_release_date);
        backdropView = findViewById(R.id.iv_detail_poster);
        voteAverageView = findViewById(R.id.tv_vote_average);
        overviewView = findViewById(R.id.tv_overview);

        Movie chosenMovie = Parcels.unwrap(getIntent().getParcelableExtra(MOVIE_DETAILS));
        originalTitleView.setText(chosenMovie.getOriginalTitle());
        releaseDateView.setText(chosenMovie.getReleaseDate());
        String posterURL = chosenMovie.getBackdropUriString();
            Picasso.with(this)
                    .load(posterURL)
                    .into(backdropView);
        voteAverageView.setText(String.valueOf(chosenMovie.getVoteAverage()));
        overviewView.setText(chosenMovie.getOverview());

    }
}
