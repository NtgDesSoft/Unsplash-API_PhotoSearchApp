package com.nelson.photosearchapp.domain

import com.google.gson.annotations.SerializedName

data class UnsplashResponse(

	@SerializedName("total") val total: Int,
	@SerializedName("total_pages") val total_pages: Int,
	@SerializedName("results") val results: List<Results>
)