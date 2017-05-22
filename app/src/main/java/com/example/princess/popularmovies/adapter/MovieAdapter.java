package com.example.princess.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.princess.popularmovies.MovieDetails;
import com.example.princess.popularmovies.R;
import com.example.princess.popularmovies.models.MovieData;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.princess.popularmovies.R.id.poster_image;

/**
 * Created by Princess on 5/16/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieData> movies;
    private Context context;
    private int rowLayout;


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout moviesLayout;
        ImageView posterImage;

        //Constructor to get widget reference
        public MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            moviesLayout = (LinearLayout) itemView.findViewById(R.id.movies_layout);
            posterImage = (ImageView) itemView.findViewById(poster_image);

        }

        @Override
        public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, MovieDetails.class);
                MovieData data = movies.get(getLayoutPosition());
                intent.putExtra("data", data);
                context.startActivity(intent);

        }
    }
    public MovieAdapter(List<MovieData> movies, int rowLayout, Context context){
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;

    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    // Get current position of item in recyclerview to bind data and assign values from list
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieData image = movies.get(position);
        String posterPath_url = "http://image.tmdb.org/t/p/w500" + image.getPosterPath();
        // load image into imageview using picasso
        Picasso.with(context).load(posterPath_url).into(holder.posterImage);

    }


    // return total item from List
    @Override
    public int getItemCount() {
        if (movies == null)
            return 0;
        return movies.size();
    }

}
