package com.kanwarabhijitsingh.ice5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddMovieActivity: AppCompatActivity() {

	private var movie: Movie? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_movie)
		setup()
	}

	private fun setup() {
		movie = intent.getSerializableExtra("Data") as Movie
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
		title.setText(movie?.title.toString())
		studio.setText(movie?.studio.toString())
		genres.setText(movie?.genres.toString())
		directors.setText(movie?.directors.toString())
		writers.setText(movie?.writers.toString())
		actors.setText(movie?.actors.toString())
		year.setText(movie?.year.toString())
		length.setText(movie?.length.toString())
		description.setText(movie?.shortDescription.toString())
		mpaRating.setText(movie?.mpaRating.toString())
		criticsRating.setText(movie?.criticsRating.toString())
	}

	private fun addMovie() {
		val title = findViewById<EditText>(R.id.title).text.toString()
		val studio = findViewById<EditText>(R.id.studio).text.toString()
		val genres = findViewById<EditText>(R.id.genres).text.toString()
		val directors = findViewById<EditText>(R.id.directors).text.toString()
		val writers = findViewById<EditText>(R.id.writers).text.toString()
		val actors = findViewById<EditText>(R.id.actors).text.toString()
		val year = findViewById<EditText>(R.id.year).text.toString().toInt()
		val length = findViewById<EditText>(R.id.length).text.toString().toInt()
		val description = findViewById<EditText>(R.id.description).text.toString()
		val mpaRating = findViewById<EditText>(R.id.mpaRating).text.toString()
		val criticsRating = findViewById<EditText>(R.id.criticsRating).text.toString().toDouble()
		val movie = Movie(
			title = title,
			studio = studio,
			genres = genres,
			directors = directors,
			writers = writers,
			actors = actors,
			year = year,
			length = length,
			shortDescription = description,
			mpaRating = mpaRating,
			criticsRating = criticsRating
		)
		MovieDatabase(this@AddMovieActivity).movieDao().addMovie(movie)
		finish()
	}

	private fun updateMovie() {
		val title = findViewById<EditText>(R.id.title).text.toString()
		val studio = findViewById<EditText>(R.id.studio).text.toString()
		val genres = findViewById<EditText>(R.id.genres).text.toString()
		val directors = findViewById<EditText>(R.id.directors).text.toString()
		val writers = findViewById<EditText>(R.id.writers).text.toString()
		val actors = findViewById<EditText>(R.id.actors).text.toString()
		val year = findViewById<EditText>(R.id.year).text.toString().toInt()
		val length = findViewById<EditText>(R.id.length).text.toString().toInt()
		val description = findViewById<EditText>(R.id.description).text.toString()
		val mpaRating = findViewById<EditText>(R.id.mpaRating).text.toString()
		val criticsRating = findViewById<EditText>(R.id.criticsRating).text.toString().toDouble()
		val movie = Movie(
			title = title,
			studio = studio,
			genres = genres,
			directors = directors,
			writers = writers,
			actors = actors,
			year = year,
			length = length,
			shortDescription = description,
			mpaRating = mpaRating,
			criticsRating = criticsRating
		)
		movie.id = this.movie?.id ?: 0
		MovieDatabase(this@AddMovieActivity).movieDao().updateMovie(movie)
		finish()
	}

}
