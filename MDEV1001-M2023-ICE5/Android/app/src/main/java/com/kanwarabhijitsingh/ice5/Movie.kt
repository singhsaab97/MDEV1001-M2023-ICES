package com.kanwarabhijitsingh.ice5

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Movie(
	@PrimaryKey(autoGenerate = true) var id: Int = 0,
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
): Serializable {

	val ratingColor: Int get() {
		if (criticsRating >= 8) {
			return Color.GREEN
		} else if (criticsRating >= 7) {
			return  Color.YELLOW
		}
		return Color.RED
	}

}
