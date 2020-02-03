package com.example.churletttomdbapi.network;


import com.example.churletttomdbapi.model.MovieDetails;
import com.example.churletttomdbapi.model.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nicolas Churlet on 21/03/2018.
 */

public interface ErpInterventionApiService {
    String API_KEY = "246b91a0";

    @GET("/?")
    Call<SearchResult> getFilmsByString(
            @Query("s") String wordInTitle,
            @Query("apikey") String apikey
    );
    //i=tt1877832&page=1&plot=full
    @GET("/?")
    Call<MovieDetails> getFilmByImdbID(
            @Query("i") String imdbID,
            @Query("apikey") String apikey,
            @Query("plot") String plot
    );
}
