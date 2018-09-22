package com.mvryan.katalogfilm.utils.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvryan.katalogfilm.BuildConfig;
import com.mvryan.katalogfilm.R;
import com.mvryan.katalogfilm.model.Film;
import com.mvryan.katalogfilm.utils.listener.FilmListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mvryan on 09/08/18.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private LinkedList<Film> filmList;
    private FilmListener filmListener;
    Context mContext;

    public FavouriteAdapter( FilmListener filmListener, Context context) {
        this.mContext = context;
        this.filmListener = filmListener;
    }

    public LinkedList<Film> getFilmList() {
        return filmList;
    }
 
    public void setFilmList(LinkedList<Film> filmList) {
        this.filmList = filmList;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, final int position) {
        holder.title.setText(filmList.get(position).getTitle());
        holder.releaseDate.setText(mContext.getString(R.string.release_date)+" : "+filmList.get(position).getRelease_date());
        holder.vote.setText(mContext.getString(R.string.vote_average)+" : "+filmList.get(position).getVote_average());
        Glide.with(holder.poster.getContext())
                .load(BuildConfig.IMG_DBMOVIE+filmList.get(position).getPoster_path())
                .into(holder.poster);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmListener.onClick(filmList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView title, releaseDate, vote;
        ImageView poster;

        public FavouriteViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_film);
            title = itemView.findViewById(R.id.title_view);
            releaseDate = itemView.findViewById(R.id.release_date_view);
            vote = itemView.findViewById(R.id.vote_view);
            poster = itemView.findViewById(R.id.poster_view);
        }
    }
}
