package com.example.churletttomdbapi.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.churletttomdbapi.R
import com.example.churletttomdbapi.activities.adapters.ShortMovieAdapter
import com.example.churletttomdbapi.model.Movie
import com.example.churletttomdbapi.model.SearchResult
import com.example.churletttomdbapi.network.ApiError
import com.example.churletttomdbapi.network.ApiHelpers
import com.example.churletttomdbapi.network.JApiRequestCallback
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.canonicalName
    }

    // region Variables declaration
    lateinit var apiHelper: ApiHelpers
    lateinit var movies : MutableList<Movie>
    lateinit var movieAdapter : ShortMovieAdapter
    // endregion Variables declaration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // region Variables initialization
        apiHelper = ApiHelpers(this)
        movies = mutableListOf()
        movieAdapter = ShortMovieAdapter(movies)
        // region RecylerView initialization
        activity_main_short_movie_recycler_view.layoutManager = LinearLayoutManager(this)
        activity_main_short_movie_recycler_view.adapter = movieAdapter
        // endregion RecylerView initialization
        // endregion Variables initialization
    }

    private fun submitStringToQueryApi(
        sentenceForResearch: String
    ) {

        if (sentenceForResearch.length > 2) {
            sentenceForResearch.replace(" ", "+", true)
            queryMoviesBySentenceByApi(sentenceForResearch)
        }

    }

    // region requestAsync
    private fun queryMoviesBySentenceByApi(sentenceForResearch: String) {
        apiHelper.getFilmsByStringAsync(
            sentenceForResearch,
            object : ApiRequestCallback<SearchResult>() {
                override fun onSuccess(result: SearchResult?) {
                    super.onSuccess(result)
                    Log.d(TAG, result.toString())
                    if (!result!!.search.isNullOrEmpty()){
                        movies.clear()
                        movies.addAll(result!!.search)
                        movieAdapter.notifyDataSetChanged()
                    }

                }

                override fun onError(error: ApiError?) {
                    super.onError(error)
                    Log.d(TAG, error.toString())
                }
            }
        )
    }
    // endregion requestAsync

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        val searchItem = menu?.findItem(R.id.menu_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(textToSubmit: String?): Boolean {
                    if (textToSubmit != null) {
                        submitStringToQueryApi(textToSubmit)
                        dismissKeyBoard()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        submitStringToQueryApi(newText)
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * A beautifull way to perform a simple thing despite the ugly native way
     */
    private fun dismissKeyBoard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(
                currentFocus?.windowToken, 0
            )
    }

    // region LifeCycle
    override fun onPause() {
        super.onPause()
        dismissKeyBoard()
    }

    override fun onStop() {
        super.onStop()
        dismissKeyBoard()
    }

    // endregion LifCycle
}
