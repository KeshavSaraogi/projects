package com.example.moviesapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesapp.model.MovieModel;
import com.example.moviesapp.response.MovieSearchResponse;
import com.example.moviesapp.BackgroundExecutors;
import com.example.moviesapp.utilities.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieAPIClient {


    private MutableLiveData<List<MovieModel>> listMovies;                               //Live Data for All Movies
    private MutableLiveData<List<MovieModel>> popularMovies;                            // Live Data for popular movies

    private static MovieAPIClient  movieAPIClientInstance;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;
    private RetrievePopularMoviesRunnable retrievePopularMoviesRunnable;

    public static MovieAPIClient getInstance() {
        if (movieAPIClientInstance == null) {
            movieAPIClientInstance = new MovieAPIClient();
        }
        return movieAPIClientInstance;
    }

    private MovieAPIClient() {
        listMovies = new MutableLiveData<>();
        popularMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return listMovies;
    }

    public LiveData<List<MovieModel>> getPopularMovies() {
        return popularMovies;
    }

    public void searchMoviesAPI(String query, int pageNumber) {

        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        //Method to retrieve data from API
        final Future handler = BackgroundExecutors
                .getInstance()
                .networkIO()
                .submit(retrieveMoviesRunnable);

        //Method to cancel retrofit call
        BackgroundExecutors
                .getInstance()
                .networkIO()
                .schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        },5000, TimeUnit.MICROSECONDS);
    }

    public void searchPopularMoviesAPI(int pageNumber) {

        if (retrievePopularMoviesRunnable != null) {
            retrievePopularMoviesRunnable = null;
        }

        retrievePopularMoviesRunnable = new RetrievePopularMoviesRunnable(pageNumber);

        //Method to retrieve data from API
        final Future handlerSecond = BackgroundExecutors
                .getInstance()
                .networkIO()
                .submit(retrievePopularMoviesRunnable);

        //Method to cancel retrofit call
        BackgroundExecutors
                .getInstance()
                .networkIO()
                .schedule(new Runnable() {
                    @Override
                    public void run() {
                        handlerSecond.cancel(true);
                    }
                },1000, TimeUnit.MICROSECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response = getMovies(query, pageNumber).execute();

                if (cancelRequest) {return;}
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if (pageNumber == 1) {
                        listMovies.postValue(list);
                    }
                    else {
                        List<MovieModel> currentMovies = listMovies.getValue();
                        currentMovies.addAll(list);
                        listMovies.postValue(currentMovies);
                    }
                }
                else {
                    String error = response.errorBody().toString();
                    Log.v("TAG", "ERROR: " + error);
                    listMovies.postValue(null);
                }
            }
            catch (IOException exception) {
                exception.printStackTrace();
                listMovies.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return Service.getMovieAPI().searchMovie(
                    Credentials.API_KEY, query, pageNumber);
        }

        private void cancelRequest() {
            Log.v("TAG","Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrievePopularMoviesRunnable implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrievePopularMoviesRunnable(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response responseSecond = getPopularMovies(pageNumber).execute();

                if (cancelRequest) {return;}
                if (responseSecond.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)responseSecond.body()).getMovies());
                    if (pageNumber == 1) {
                        popularMovies.postValue(list);
                    }
                    else {
                        List<MovieModel> currentMovies = popularMovies.getValue();
                        currentMovies.addAll(list);
                        popularMovies.postValue(currentMovies);
                    }
                }
                else {
                    String error = responseSecond.errorBody().toString();
                    Log.v("TAG", "ERROR: " + error);
                    popularMovies.postValue(null);
                }
            }
            catch (IOException exception) {
                exception.printStackTrace();
                popularMovies.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getPopularMovies(int pageNumber) {
            return Service.getMovieAPI().getPopular(
                    Credentials.API_KEY, pageNumber);
        }

        private void cancelRequest() {
            Log.v("TAG","Cancelling Search Request");
            cancelRequest = true;
        }
    }

}
