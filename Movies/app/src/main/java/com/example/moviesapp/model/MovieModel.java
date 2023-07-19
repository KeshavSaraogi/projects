package com.example.moviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieModel implements Parcelable {

    private int movieID;
    private String title;
    private String posterPath;
    private String releaseDate;
    private String movieOverview;
    private float voteAverage;
    private int runtime;

    public MovieModel(int movieID, String title, String posterPath, String releaseDate, String movieOverview, float voteAverage, int runtime) {
        this.movieID = movieID;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.movieOverview = movieOverview;
        this.voteAverage = voteAverage;
        this.runtime = runtime;
    }

    protected MovieModel(Parcel in) {
        movieID = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        movieOverview = in.readString();
        voteAverage = in.readFloat();
        runtime = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(movieID);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(movieOverview);
        dest.writeFloat(voteAverage);
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getRuntime() {
        return runtime;
    }
}
