package com.example.princess.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import com.example.princess.popularmovies.models.MovieData;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailsActivity extends AppCompatActivity {

    private Context context;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.poster_image_details) ImageView image;
    @BindView(R.id.release_date) TextView releaseDate;
    @BindView(R.id.rating) TextView ratings;
    @BindView(R.id.overview) TextView overView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        MovieData data = getIntent().getParcelableExtra("data");

        String posterPath_url = "http://image.tmdb.org/t/p/w185" + data.getPosterPath();
        // load image into imageview using picasso
        Picasso.with(context).load(posterPath_url).placeholder(R.drawable.placeholder).into(image);
        title.setText(data.getTitle());
        releaseDate.setText(data.getDate());
        ratings.setText(data.getRating().toString());
        overView.setText(data.getOverview());

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
