package com.codepath.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.myapplication.Models.Movies;
import com.codepath.myapplication.adapters.MoviesAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private static final String MOVIE_URL="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    List<Movies> movies;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
           final MoviesAdapter adapter = new MoviesAdapter(this,movies);
           rvMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
           rvMovies.setAdapter(adapter);


           AsyncHttpClient client = new AsyncHttpClient();
           client.get(MOVIE_URL, new JsonHttpResponseHandler() {

               @Override
               public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   //super.onSuccess(statusCode, headers, response);
                   try {
                       JSONArray movieJsonArray=response.getJSONArray("results");
                       movies.addAll(Movies.fromJsonArray(movieJsonArray));
                       adapter.notifyDataSetChanged();
                       Log.d("smile",movies.toString());
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }

               @Override
               public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                   super.onFailure(statusCode, headers, responseString, throwable);
               }
           });


    }
}
