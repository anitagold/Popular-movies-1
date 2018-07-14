/**
 * Created by Anita Goldpergel (anita.goldpergel@gmail.com) on 2018.05.23.
 *
 * * * MOVIEADAPTER Class
 *
 */

package hu.bubbanet.popularmovies;

import android.content.Context;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    public interface OnItemClickListener  {
        void onItemClick(Movie item);
    }

    private Context context;
    private List<Movie> movies;
    private final OnItemClickListener listener;

    public MovieAdapter(Context context, List<Movie> movies, OnItemClickListener listener) {
        this.context = context;
        this.movies = movies;
        this.listener = listener;
    }

    @Override
    public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_item, viewGroup, false);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        final Movie movie = movies.get(position);

        String voteString = String.valueOf(movie.getVoteAverage());
        holder.titleView.setText("Rating: " + voteString + "/10");

        Picasso.with(context)
                .load(movie.getImageUriString())
                .into(holder.posterView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == movies) return 0;
        return movies.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView posterView;
        TextView titleView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_item);
            posterView = itemView.findViewById(R.id.iv_thumbnail);
            titleView = itemView.findViewById(R.id.tv_movie_title);
        }
    }

    public void setMovieList(List<Movie> movieList) {
        movies = movieList;
        notifyDataSetChanged();
    }
}
