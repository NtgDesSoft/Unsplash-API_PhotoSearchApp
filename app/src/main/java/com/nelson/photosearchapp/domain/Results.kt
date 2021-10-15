package com.nelson.photosearchapp.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Results(
	@SerializedName("id") val id: String,
	@SerializedName("created_at") val created_at: String,
	@SerializedName("width") val width: Int,
	@SerializedName("height") val height: Int,
	@SerializedName("color") val color: String,
	@SerializedName("blur_hash") val blur_hash: String,
	@SerializedName("likes") val likes: Int,
	@SerializedName("liked_by_user") val liked_by_user: Boolean,
	@SerializedName("description") val description: String,
	@SerializedName("user") val user: User,
	@SerializedName("current_user_collections") val current_user_collections: List<String>,
	@SerializedName("urls") val urls: Urls,
	@SerializedName("links") val links: Links
) : Serializable