package com.example.princess.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import com.example.princess.popularmovies.models.MovieData;



public class MovieDetails extends AppCompatActivity {

    private Context context;
    TextView title;
    ImageView image;
    TextView releaseDate;
    TextView ratings;
    TextView overView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        title = (TextView) findViewById(R.id.title);
        image = (ImageView) findViewById(R.id.backdrop_image);
        releaseDate = (TextView) findViewById(R.id.release_date);
        ratings = (TextView) findViewById(R.id.rating);
        overView = (TextView) findViewById(R.id.overview);

        MovieData data = getIntent().getParcelableExtra("data");

        String backDrop_url = "http://image.tmdb.org/t/p/w500" + data.getBackDrop();
        // load image into imageview using picasso
        Picasso.with(context).load(backDrop_url).into(image);
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
