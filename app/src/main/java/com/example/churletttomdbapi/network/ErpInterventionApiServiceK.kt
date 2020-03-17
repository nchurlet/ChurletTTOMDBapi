package com.example.churletttomdbapi.network;

import com.example.churletttomdbapi.model.MovieDetails
import com.example.churletttomdbapi.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Nicolas Churlet on 17/03/2020.
 */
interface ErpInterventionApiService {
    @GET("/?")
    fun getFilmsByString(
        @Query("s") wordInTitle: String?,
        @Query("apikey") apikey: String?
    ): Call<SearchResult?>

    //i=tt1877832&page=1&plot=full
    @GET("/?")
    fun getFilmByImdbID(
        @Query("i") imdbID: String?,
        @Query("apikey") apikey: String?,
        @Query("plot") plot: String?
    ): Call<MovieDetails?>
}