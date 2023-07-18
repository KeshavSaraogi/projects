package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.moviesapp.model.MovieModel;
import com.example.moviesapp.request.Service;
import com.example.moviesapp.response.MovieResponse;
import com.example.moviesapp.response.MovieSearchResponse;
import com.example.moviesapp.utilities.Credentials;
import com.example.moviesapp.utilities.MovieAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRetrofitResponseByID();
            }
        });
    }

    private void getRetrofitResponse() {
        MovieAPI movieAPI = Service.getMovieAPI();
        Call<MovieSearchResponse> movieSearchResponseCall = movieAPI.searchMovie(
                Credentials.API_KEY, "Jack Reacher", 1);

        movieSearchResponseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    Log.v("TAG", "The Response " + response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    for (MovieModel movie: movies) {
                        Log.v("TAG", "THE RELEASE DATE: " + movie.getReleaseDate());
                    }
                }
                else {
                    Log.v("TAG","ERROR" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    private void getRetrofitResponseByID() {
        MovieAPI movieAPI = Service.getMovieAPI();
        Call<MovieModel> responseCall = movieAPI.
                getMovie(343611, Credentials.API_KEY);
        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {
                    MovieModel movie = response.body();
                    Log.v("TAG", "The Response Is " + movie.getTitle());
                }
                else {
                    Log.v("TAG", "Error " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }
}
