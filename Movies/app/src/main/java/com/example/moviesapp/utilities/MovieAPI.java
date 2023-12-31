package com.example.moviesapp.utilities;

import com.example.moviesapp.model.MovieModel;
import com.example.moviesapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET("3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String api_key,
            @Query("query")  String query,
            @Query("page") int page);


    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movieID,
            @Query("api_key") String api_key
    );

    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String api_key,
            @Query("key") int page
    );
}
