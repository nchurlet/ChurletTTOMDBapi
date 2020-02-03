package com.example.churletttomdbapi.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.churletttomdbapi.R
import com.example.churletttomdbapi.model.MovieDetails
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_movie.*

class DetailsMovieActivity : AppCompatActivity(){
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_movie)


        val movieData =
            gson.fromJson(intent.getStringExtra("MovieData"), MovieDetails::class.java)

        activity_details_movie_title.text = movieData.title
        activity_details_movie_released_date_value.text = movieData.released
        activity_details_movie_synopsis_value.text = movieData.plot
        activity_details_movie_casting_value.text = movieData.actors

        Picasso.get()
            .load(movieData.poster)
            .placeholder(R.drawable.ic_local_movies_black_24dp)
            .into(activity_details_movie_poster)

        activity_details_movie_critics_value.rating = (movieData.imdbRating.toFloat() / 2)

    }
}