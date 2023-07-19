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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((MovieViewHolder)holder).title.setText(listMovies.get(position).getTitle());
        ((MovieViewHolder)holder).releaseDate.setText(listMovies.get(position).getReleaseDate());
        ((MovieViewHolder)holder).duration.setText(listMovies.get(position).getRuntime());
        ((MovieViewHolder)holder).ratingBar.setRating(listMovies.get(position).getVoteAverage() / 2);

        Glide.with(holder.itemView.getContext())
                .load(listMovies.get(position))
                .into(((MovieViewHolder)holder).imageView);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
