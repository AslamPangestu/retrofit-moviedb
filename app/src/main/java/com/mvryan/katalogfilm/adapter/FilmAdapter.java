package com.mvryan.katalogfilm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvryan.katalogfilm.R;
import com.mvryan.katalogfilm.model.Film;

import java.util.List;

/**
 * Created by mvryan on 09/08/18.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private List<Film> filmList;

    public FilmAdapter(List<Film> filmList) {
        this.filmList = filmList;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.title.setText(filmList.get(position).getTitle());
        holder.releaseDate.setText(filmList.get(position).getRelease_date());
        holder.vote.setText(String.valueOf(filmList.get(position).getVote_average()));
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView title, releaseDate, vote;

        public FilmViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_film);
            title = itemView.findViewById(R.id.title_view);
            releaseDate = itemView.findViewById(R.id.release_date_view);
            vote = itemView.findViewById(R.id.vote_view);
        }
    }
}
