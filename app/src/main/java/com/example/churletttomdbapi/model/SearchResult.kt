package com.example.churletttomdbapi.model

import com.google.gson.annotations.SerializedName

data class SearchResult (

	@SerializedName("Search") val search : List<Movie>,
	@SerializedName("totalResults") val totalResults : Int,
	@SerializedName("Response") val response : Boolean
)