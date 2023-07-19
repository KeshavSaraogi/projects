package com.example.moviesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.model.MovieModel;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MovieModel> listMovies;
    private OnMovieListener onMovieListener;
    private static final int DISPLAY_UP = 1;
    private static final int DISPLAY_SEARCH = 2;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == DISPLAY_SEARCH) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
            return new MovieViewHolder(view, onMovieListener);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
            return new PopularMovieViewHolder(view, onMovieListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == DISPLAY_SEARCH){
            ((MovieViewHolder)holder).ratingBar.setRating(listMovies.get(position).getVoteAverage() / 2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/pw500/" + listMovies.get(position).getPosterPath())
                    .into(((MovieViewHolder)holder).imageView);
        }
        else {
            ((PopularMovieViewHolder)holder).ratingBar.setRating(listMovies.get(position).getVoteAverage() / 2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/pw500/" + listMovies.get(position).getPosterPath())
                    .into(((MovieViewHolder)holder).imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (listMovies != null) {
            return listMovies.size();
        }
        return 0;
    }

    public void setListMovies(List<MovieModel> listMovies) {
        this.listMovies = listMovies;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position) {
        if (listMovies != null) {
            if (listMovies.size() > 0) {
                return listMovies.get(position);
            }
        }
        return null;
    }
}
