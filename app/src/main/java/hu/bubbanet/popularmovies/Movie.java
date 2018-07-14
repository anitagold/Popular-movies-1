/**
 * Created by Anita Goldpergel (anita.goldpergel@gmail.com) on 2018.06.05.
 *
 * * * MOVIE Class
 *
 * Android Parcelables made easy through code generation. http://parceler.org
 * Parcelables are a great way to serialize Java Objects between Contexts.
 * Compared with traditional Serialization, Parcelables take on the order of 10x less time
 * to both serialize and deserialize.
  */


package hu.bubbanet.popularmovies;

import android.net.Uri;

import org.parceler.Parcel;

@Parcel
public class Movie {

    public int id;
    public String originalTitle;
    public String posterPath;
    public String MOVIE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    public String MOVIE_BACKDROP_URL = "http://image.tmdb.org/t/p/w780/";

    public String backdropPath;
    public String releaseDate;
    public double voteAverage;
    public String overview;

    public Movie() {
    }

    public Movie(int id, String originalTitle, String posterPath, String backdropPath, String releaseDate, double voteAverage, String overview) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropUriString() {
        Uri baseUri = Uri.parse(MOVIE_BACKDROP_URL);
        Uri.Builder backdropUri = baseUri.buildUpon();
        backdropUri.appendEncodedPath(backdropPath);
        String backdropUriString = backdropUri.toString();
        return backdropUriString;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getImageUriString() {
        Uri baseUri = Uri.parse(MOVIE_POSTER_URL);
        Uri.Builder imageUri = baseUri.buildUpon();
        imageUri.appendEncodedPath(posterPath);
        String imageUriString = imageUri.toString();
        return imageUriString;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
