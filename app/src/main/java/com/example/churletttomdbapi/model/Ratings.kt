package com.example.churletttomdbapi.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class Ratings (

	@SerializedName("Source") val source : String,
	@SerializedName("Value") @Nullable val value : String
)