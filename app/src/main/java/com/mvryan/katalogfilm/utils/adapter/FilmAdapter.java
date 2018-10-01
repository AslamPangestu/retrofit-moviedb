package com.mvryan.katalogfilm.utils.adapter;

import android.content.Context;
import android.database.Cursor;
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

import java.util.List;

/**
 * Created by mvryan on 09/08/18.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private List<Film> filmList;
    private FilmListener filmListener;
    private Context mContext;

    public FilmAdapter(List<Film> filmList, FilmListener filmListener, Context context) {
        this.mContext = context;
        this.filmList = filmList;
        this.filmListener = filmListener;
    }

    public List<Film> getFilmList() {
        return filmList;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilmViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, final int position) {
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

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView title, releaseDate, vote;
        ImageView poster;

        public FilmViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_film);
            title = itemView.findViewById(R.id.title_view);
            releaseDate = itemView.findViewById(R.id.release_date_view);
            vote = itemView.findViewById(R.id.vote_view);
            poster = itemView.findViewById(R.id.poster_view);
        }
    }
}
