package com.example.churletttomdbapi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import com.example.churletttomdbapi.R
import com.example.churletttomdbapi.model.Movie
import com.example.churletttomdbapi.model.SearchResult
import com.example.churletttomdbapi.network.ApiError
import com.example.churletttomdbapi.network.ApiHelpers
import com.example.churletttomdbapi.network.ApiRequestCallback

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.canonicalName
    }

    lateinit var apiHelper: ApiHelpers
    lateinit var movies : MutableList<Movie>
    var isSentenceChecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        movies = mutableListOf()
        apiHelper = ApiHelpers(this)
    }

    private fun submitStringToQueryApi(
        sentenceForResearch: String
    ) {

        if (sentenceForResearch.length > 2) {
            sentenceForResearch.replace(" ", "+", true)
            queryMoviesFromSentenceByApi(sentenceForResearch)

        }

    }

    private fun queryMoviesFromSentenceByApi(sentenceForResearch: String) {
        apiHelper.getFilmsByStringAsync(
            sentenceForResearch,
            object : ApiRequestCallback<SearchResult>() {
                override fun onSuccess(result: SearchResult?) {
                    super.onSuccess(result)
                    Log.d(TAG, result.toString())
                    if (!result!!.search.isNullOrEmpty()){
                        movies.addAll(result!!.search)
                    }

                }

                override fun onError(error: ApiError?) {
                    super.onError(error)
                    Log.d(TAG, error.toString())
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        val searchItem = menu?.findItem(R.id.menu_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(textToSubmit: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        submitStringToQueryApi(
                            newText)
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }
}
