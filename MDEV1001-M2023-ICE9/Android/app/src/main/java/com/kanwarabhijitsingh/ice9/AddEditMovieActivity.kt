package com.kanwarabhijitsingh.ice9

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AddEditMovieActivity: AppCompatActivity() {

	private var movie: Movie? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_edit_movie)
		setup()
	}

	private fun setup() {
		movie = intent.getSerializableExtra("Data") as? Movie
		val addButton = findViewById<Button>(R.id.addButton)
		if (movie == null) {
			addButton.text = "Add Movie"
		} else {
			fillMovieDetails()
			addButton.text = "Update Movie"
		}
		addButton.setOnClickListener {
			if (movie == null) {
				addMovie()
			} else {
				updateMovie()
			}
		}
	}

	private fun fillMovieDetails() {
		val title = findViewById<EditText>(R.id.title)
		val studio = findViewById<EditText>(R.id.studio)
		val genres = findViewById<EditText>(R.id.genres)
		val directors = findViewById<EditText>(R.id.directors)
		val writers = findViewById<EditText>(R.id.writers)
		val actors = findViewById<EditText>(R.id.actors)
		val year = findViewById<EditText>(R.id.year)
		val length = findViewById<EditText>(R.id.length)
		val description = findViewById<EditText>(R.id.description)
		val mpaRating = findViewById<EditText>(R.id.mpaRating)
		val criticsRating = findViewById<EditText>(R.id.criticsRating)
		title.setText(movie?.title)
		studio.setText(movie?.studio)
		genres.setText(movie?.genres.toString())
		directors.setText(movie?.directors.toString())
		writers.setText(movie?.writers.toString())
		actors.setText(movie?.actors.toString())
		year.setText(movie?.year.toString())
		length.setText(movie?.length.toString())
		description.setText(movie?.description)
		mpaRating.setText(movie?.mpaRating.toString())
		criticsRating.setText(movie?.criticsRating.toString())
	}

	private fun addMovie() {
		val movieId = UUID.randomUUID().toString()
		val movie = getUpdatedMovie(movieId, movieId)

		val service = RetrofitClient.moviesApiService
		CoroutineScope(Dispatchers.Main).launch {
			try {
				withContext(Dispatchers.IO) {
					service.addMovie(movie)
				}
			} catch (e: Exception) {
				showToast("Failed to add movie: ${e.message}")
			} finally {
				// Exit the activity
				finish()
			}
		}
	}

	private fun updateMovie() {
		movie?.let {
			val movie = getUpdatedMovie(it.id, movie?.movieID)

			val service = RetrofitClient.moviesApiService
			CoroutineScope(Dispatchers.Main).launch {
				try {
					withContext(Dispatchers.IO) {
						service.updateMovie(it.id, movie)
					}
				} catch (e: Exception) {
					showToast("Failed to update movie: ${e.message}")
				} finally {
					// Exit the activity
					finish()
				}
			}
		}
	}

	private fun getUpdatedMovie(id: String?, movieID: String?): Movie {
		val title = findViewById<EditText>(R.id.title).text.toString()
		val studio = findViewById<EditText>(R.id.studio).text.toString()
		val genres = findViewById<EditText>(R.id.genres).text.toString().split(",").toList()
		val directors = findViewById<EditText>(R.id.directors).text.toString().split(",").toList()
		val writers = findViewById<EditText>(R.id.writers).text.toString().split(",").toList()
		val actors = findViewById<EditText>(R.id.actors).text.toString().split(",").toList()
		val year = findViewById<EditText>(R.id.year).text.toString().toInt()
		val length = findViewById<EditText>(R.id.length).text.toString().toInt()
		val description = findViewById<EditText>(R.id.description).text.toString()
		val mpaRating = findViewById<EditText>(R.id.mpaRating).text.toString()
		val criticsRating = findViewById<EditText>(R.id.criticsRating).text.toString().toDouble()
		val movieId = UUID.randomUUID().toString()
		return Movie(
			id = id ?: movieId,
			movieID = movieID ?: movieId,
			title = title,
			studio = studio,
			genres = genres,
			directors = directors,
			writers = writers,
			actors = actors,
			year = year,
			length = length,
			description = description,
			mpaRating = mpaRating,
			criticsRating = criticsRating
		)
	}

	private fun showToast(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
	}
}
