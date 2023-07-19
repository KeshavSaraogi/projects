package com.example.moviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.moviesapp.adapters.MovieRecyclerView;
import com.example.moviesapp.adapters.OnMovieListener;
import com.example.moviesapp.model.MovieModel;
import com.example.moviesapp.request.Service;
import com.example.moviesapp.response.MovieResponse;
import com.example.moviesapp.response.MovieSearchResponse;
import com.example.moviesapp.utilities.Credentials;
import com.example.moviesapp.utilities.MovieAPI;
import com.example.moviesapp.viewmodel.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerViewAdapter;

    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupSearchView();

        recyclerView = findViewById(R.id.recyclerView);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        observeAnyChange();
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        movieRecyclerViewAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (!recyclerView.canScrollHorizontally(1)){
                movieListViewModel.searchNextPage();
            }

        }
    });

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecyclerViewAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void setupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieAPI(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void observeAnyChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    for (MovieModel movieModel: movieModels) {
                        Log.v("TAG","On Changed: " + movieModel.getTitle());
                        movieRecyclerViewAdapter.setListMovies(movieModels);
                    }
                }
            }
        });
    }

    private void searchMovieAPI(String query, int pageNumber) {
        movieListViewModel.searchMovieAPI(query, pageNumber);
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
