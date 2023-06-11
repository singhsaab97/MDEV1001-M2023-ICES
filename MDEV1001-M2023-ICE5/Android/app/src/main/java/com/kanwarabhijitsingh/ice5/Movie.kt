package com.kanwarabhijitsingh.ice5

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val title: String,
	val studio: String,
	val genres: String,
	val directors: String,
	val writers: String,
	val actors: String,
	val year: Int,
	val length: Int,
	val shortDescription: String?,
	val mpaRating: String,
	val criticsRating: Double
)
