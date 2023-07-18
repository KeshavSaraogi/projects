package com.example.moviesapp.request;

import com.example.moviesapp.utilities.Credentials;
import com.example.moviesapp.utilities.MovieAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = retrofitBuilder.build();
    private static MovieAPI movieAPI = retrofit.create(MovieAPI.class);

    public MovieAPI getMovieAPI() {
        return movieAPI;
    }
}
