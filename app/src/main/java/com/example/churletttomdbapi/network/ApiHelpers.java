package com.example.churletttomdbapi.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.churletttomdbapi.model.MovieDetails;
import com.example.churletttomdbapi.model.SearchResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nicolas Churlet on
.
 */

public class ApiHelpers {

    public static final String API_KEY = "246b91a0";
    public static final String ENDPOINT = "http://www.omdbapi.com/";

    private ErpInterventionApiService apiservice;

    final private Context _context;

    SharedPreferences preferences;
    public ApiHelpers(Context context) {

        this._context = context;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        // region OkHttpClient
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        // endregion OkHttpClient

        // region API service
        apiservice = new Retrofit
                .Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(ErpInterventionApiService.class);
        // endregion API service
    }

    public void getFilmsByStringAsync (
            String wordInTitle,
            ApiRequestCallback<SearchResult> callback
    ) {
        (new ApiRequest<SearchResult>())
                .requestAsync(
                        apiservice.getFilmsByString(wordInTitle, API_KEY),
                        callback
                );
    }

    public void getFilmByImdbIDAsync (
            String imdbID,
            ApiRequestCallback<MovieDetails> callback
    ) {
        (new ApiRequest<MovieDetails>())
                .requestAsync(
                        apiservice.getFilmByImdbID(imdbID, API_KEY, "full"),
                        callback
                );
    }
}

