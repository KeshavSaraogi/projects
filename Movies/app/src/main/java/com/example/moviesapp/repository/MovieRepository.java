package com.example.moviesapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesapp.model.MovieModel;
import com.example.moviesapp.request.MovieAPIClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository movieRepositoryInstance;
    private MovieAPIClient movieAPIClient;
    private String movieQuery;
    private int moviePageNumber;

    public static MovieRepository getInstance() {
        if (movieRepositoryInstance == null) {
            movieRepositoryInstance = new MovieRepository();
        }
        return movieRepositoryInstance;
    }

    public MovieRepository() {
        movieAPIClient = MovieAPIClient.getInstance();
    }

    public MovieAPIClient getMovieAPIClient() {
        return movieAPIClient;
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieAPIClient.getMovies();
    }

    public LiveData<List<MovieModel>> getPopular() {
        return movieAPIClient.getPopularMovies();
    }

    public void searchMovieAPI(String query, int pageNumber) {
        movieQuery = query;
        moviePageNumber = pageNumber;
        searchMovieAPI(query, pageNumber);
    }

    public void searchPopularMovieAPI(int pageNumber) {
        moviePageNumber = pageNumber;
        movieAPIClient.searchPopularMoviesAPI(pageNumber);
    }

    public void searchNextPage() {
        searchMovieAPI(movieQuery, moviePageNumber + 1);
    }
}
