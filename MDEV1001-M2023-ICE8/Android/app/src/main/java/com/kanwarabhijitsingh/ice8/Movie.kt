package com.kanwarabhijitsingh.ice8

import com.squareup.moshi.Json

data class Movie(
	@Json(name = "_id") val id: String,
	@Json(name = "movieID") val movieID: String,
	val title: String,
	val studio: String,
	val genres: List<String>,
	val directors: List<String>,
	val writers: List<String>,
	val actors: List<String>,
	val year: Int,
	val length: Int,
	@Json(name = "shortDescription") val description: String?,
	val mpaRating: String,
	val criticsRating: Double,
	var isExpanded: Boolean = false
)
