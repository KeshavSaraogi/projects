package com.example.moviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.moviesapp.adapters.MovieRecyclerView;
import com.example.moviesapp.adapters.OnMovieListener;
import com.example.moviesapp.model.MovieModel;
import com.example.moviesapp.viewmodel.MovieListViewModel;

import java.util.List;


public class MovieListActivity extends AppCompatActivity implements OnMovieListener {
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;
    private MovieListViewModel movieListViewModel;
    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SetupSearchView();

        recyclerView = findViewById(R.id.recyclerView);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
        ObservePopular();
        movieListViewModel.searchPopularMovieAPI(1);
        Log.v("TAG Y", "IS POPULAR: " +isPopular);
    }

    private void ObservePopular(){
        movieListViewModel.getPopularMovie().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null){
                    for (MovieModel movieModel: movieModels){
                        movieRecyclerAdapter.setMovies(movieModels);
                    }
                }
            }
        });
    }

    // Observing any data change
    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null){
                    for (MovieModel movieModel: movieModels){
                        movieRecyclerAdapter.setMovies(movieModels);
                    }
                }
            }
        });
    }

    private void ConfigureRecyclerView() {
        movieRecyclerAdapter = new MovieRecyclerView( this);
        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecyclerAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = false;
                Log.v("TAG Y", "IS POPULAR: " +isPopular);

            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

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
}

