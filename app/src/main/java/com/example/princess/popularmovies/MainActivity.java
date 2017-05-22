package com.example.princess.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    protected void onResume() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isPopularMovie = sharedPreferences.getBoolean("popular_key", false);
        boolean isTopratedMovie = sharedPreferences.getBoolean("toprated_key", false);


        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Please obtain your API KEY first from themoviedb.org",
                    Toast.LENGTH_LONG).show();
            return;
        }

        mRecylerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layout = new GridLayoutManager(this, 2);
        mRecylerView.setLayoutManager(layout);

        isConnected = ConnectionTest.isNetworkAvailable(this);
        if (isConnected) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            if (isPopularMovie) {
                Call<MovieResponse> call = apiService.getPopularMovies(API_KEY);
                call.enqueue(new Callback<MovieResponse>() {

                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        List<MovieData> movies = response.body().getResults();
                        mRecylerView.setAdapter(new MovieAdapter(movies, R.layout.movies_list_item, getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        //Log error here if request fails
                        Log.e(TAG, t.toString());
                    }
                });
            }
            else if (isTopratedMovie) {
                Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
                call.enqueue(new Callback<MovieResponse>() {

                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        List<MovieData> movies = response.body().getResults();
                        mRecylerView.setAdapter(new MovieAdapter(movies, R.layout.movies_list_item, getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        //Log error here if request fails
                        Log.e(TAG, t.toString());
                    }
                });
            }
            else {
                Call<MovieResponse> call = apiService.getPopularMovies(API_KEY);
                call.enqueue(new Callback<MovieResponse>() {

                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        List<MovieData> movies = response.body().getResults();
                        mRecylerView.setAdapter(new MovieAdapter(movies, R.layout.movies_list_item, getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        //Log error here if request fails
                        Log.e(TAG, t.toString());
                    }
                });
            }

        }
        else {

            Toast.makeText(this, R.string.toast, Toast.LENGTH_LONG).show();

            return;
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
