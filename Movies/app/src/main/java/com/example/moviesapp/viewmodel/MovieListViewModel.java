package com.example.moviesapp.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesapp.model.MovieModel;
import com.example.moviesapp.repository.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }

    public void searchMovieAPI(String query, int pageNumber) {
        movieRepository.searchMovieAPI(query, pageNumber);
    }

    public void searchNextPage() {
        movieRepository.searchNextPage();
    }
}
