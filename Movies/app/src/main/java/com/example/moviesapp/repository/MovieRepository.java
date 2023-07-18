package com.example.moviesapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesapp.model.MovieModel;

import java.util.List;

public class MovieRepository {

    private static MovieRepository movieRepositoryInstance;
    private MutableLiveData<List<MovieModel>> listMovies;

    public static MovieRepository getInstance() {
        if (movieRepositoryInstance == null) {
            movieRepositoryInstance = new MovieRepository();
        }
        return movieRepositoryInstance;
    }

    public MovieRepository() {
        listMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return listMovies;
    }
}

