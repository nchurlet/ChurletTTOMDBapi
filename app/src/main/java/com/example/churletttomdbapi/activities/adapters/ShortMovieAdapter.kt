package com.example.churletttomdbapi.activities.adapters;

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.churletttomdbapi.R
import com.example.churletttomdbapi.activities.DetailsMovieActivity
import com.example.churletttomdbapi.model.Movie
import com.example.churletttomdbapi.model.MovieDetails
import com.example.churletttomdbapi.network.ApiError
import com.example.churletttomdbapi.network.ApiHelpers
import com.example.churletttomdbapi.network.ApiRequestCallback
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_short_card.view.*


class ShortMovieAdapter(private val myDataset: MutableList<Movie>)  : RecyclerView.Adapter<ShortMovieAdapter.ShortMovieViewHolder>() {

    class ShortMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val _poster = itemView.item_short_movie_poster
        val _title = itemView.item_short_movie_title
        val _type = itemView.item_short_movie_type
        val _year = itemView.item_short_movie_year
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortMovieViewHolder {
        return ShortMovieViewHolder( itemView =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_movie_short_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ShortMovieViewHolder, position: Int) {
        val context = holder.itemView.context
        bindData(holder, position, context)
        setClickListener(holder, position, context)
    }

    //region specific methods
    private fun setClickListener(
        holder: ShortMovieViewHolder,
        position: Int,
        context: Context
    ) {
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsMovieActivity::class.java)
            val apiHelpers = ApiHelpers(context)

            apiHelpers.getFilmByImdbIDAsync(
                myDataset[position].imdbID,
                object : ApiRequestCallback<MovieDetails>() {
                    override fun onSuccess(result: MovieDetails?) {
                        super.onSuccess(result)

                        var gson = Gson()
                        intent.putExtra("MovieData", gson.toJson(result))
                        context.startActivity(intent)
                    }

                    override fun onError(error: ApiError?) {
                        super.onError(error)
                        Toast.makeText(context, error!!.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun bindData(
        holder: ShortMovieViewHolder,
        position: Int,
        context: Context
    ) {
        holder._title.text = myDataset[position].title
        holder._type.text = myDataset[position].type
        holder._year.text = myDataset[position].year
        Picasso.get()
            .load(myDataset[position].poster)
            .placeholder(R.drawable.ic_local_movies_black_24dp)
            .into(holder._poster)

        setBackground(position, holder,context)
    }

    private fun setBackground(
        position: Int,
        holder: ShortMovieViewHolder,
        context: Context
    ) {
        val isOdd = (position % 2 == 1)
        if (isOdd) {
            holder.itemView.background = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.background_short_movie_card_odd,
                null
            )
        } else {
            holder.itemView.background = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.background_short_movie_card_even,
                null
            )
        }
    }
    //endregion specific methods

    override fun getItemCount() = myDataset.size
}
