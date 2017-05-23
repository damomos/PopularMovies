package com.example.princess.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.princess.popularmovies.adapter.MovieAdapter;
import com.example.princess.popularmovies.models.MovieData;
import com.example.princess.popularmovies.models.MovieResponse;
import com.example.princess.popularmovies.rest.ApiClient;
import com.example.princess.popularmovies.rest.ApiInterface;
import com.example.princess.popularmovies.utils.ConnectionTest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private  RecyclerView mRecylerView;
    boolean isConnected;
    private static SharedPreferences sharedPreferences;

    private final static String API_KEY = BuildConfig.MY_API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.api_key_error_message, Toast.LENGTH_LONG).show();
        }
    }

    public void popular(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieData> movies = response.body().getResults();
                mRecylerView.setAdapter(new MovieAdapter(movies, R.layout.movies_list_item, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.failure_message, Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }

    public void topRated(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieData> movies = response.body().getResults();
                mRecylerView.setAdapter(new MovieAdapter(movies, R.layout.movies_list_item, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.failure_message, Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();

        mRecylerView = (RecyclerView) findViewById(R.id.recyclerView);

        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecylerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            mRecylerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isPopularMovie = sharedPreferences.getBoolean("popular_key", false);
        boolean isTopratedMovie = sharedPreferences.getBoolean("toprated_key", false);

        isConnected = ConnectionTest.isNetworkAvailable(this);
        if (isConnected) {

            if (isPopularMovie) {
                popular();
            }
            else if (isTopratedMovie) {
                topRated();
            }
            else {
                popular();
            }
        }
        else {

            Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.settings_menu) {
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
