package com.kanwarabhijitsingh.ice6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException
import java.nio.charset.Charset
import com.google.gson.Gson

class MoviesActivity: AppCompatActivity() {

	private var mAdapter: MoviesAdapter? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_movies)
		setup()
	}

	override fun onResume() {
		super.onResume()
		val movies = MovieDatabase(this@MoviesActivity).movieDao().getAllMovies()
		mAdapter = MoviesAdapter()
		val recyclerView = findViewById<RecyclerView>(R.id.moviesRecyclerView)
		recyclerView.apply {
			layoutManager = LinearLayoutManager(this@MoviesActivity)
			adapter = mAdapter
			setAdapter(movies)
			mAdapter?.setDeleteActionListener { movie ->
				val builder = AlertDialog.Builder(this@MoviesActivity)
				builder.setTitle("Delete " + movie.title + "?")
				builder.setMessage("This action will delete it from the database permanently")
				builder.setPositiveButton("Delete") { alert, _ ->
					MovieDatabase(this@MoviesActivity).movieDao().deleteMovie(movie)
					val movies = MovieDatabase(this@MoviesActivity).movieDao().getAllMovies()
					setAdapter(movies)
					alert.dismiss()
				}
				builder.setNegativeButton("Cancel") { alert, _ ->
					alert.dismiss()
				}
				builder.create().show()
			}
			mAdapter?.setEditActionListener {
				val intent = Intent(this@MoviesActivity, AddEditMovieActivity::class.java)
				intent.putExtra("Data", it)
				startActivity(intent)
			}
		}
	}

	private fun setup() {
		val database = MovieDatabase(this)
		// Fetch movies
		var savedMovies = database.movieDao().getAllMovies()
		if (savedMovies.isEmpty()) {
			val movies = parseJsonFileToMovies(database)
			savedMovies = movies
			movies.forEach {
				database.movieDao().addMovie(it)
			}
		}
		// Init recycler view
		val recyclerView = findViewById<RecyclerView>(R.id.moviesRecyclerView)
		recyclerView.apply {
			layoutManager = LinearLayoutManager(this@MoviesActivity)
			adapter = MoviesAdapter().apply {
				reset(savedMovies)
			}
		}
		// Add click listener
		val addButton = findViewById<FloatingActionButton>(R.id.addButton)
		addButton.setOnClickListener {
			val intent = Intent(this, AddEditMovieActivity::class.java)
			startActivity(intent)
		}
	}

	private fun setAdapter(movies: List<Movie>) {
		mAdapter?.reset(movies)
	}

	private fun parseJsonFileToMovies(db: MovieDatabase): List<Movie> {
		val json: String = try {
			val inputStream = assets.open("Movies.json")
			val size = inputStream.available()
			val buffer = ByteArray(size)
			inputStream.read(buffer)
			inputStream.close()
			String(buffer, Charset.defaultCharset())
		} catch (e: IOException) {
			e.printStackTrace()
			return emptyList()
		}
		// Return movies list
		val gson = Gson()
		return gson.fromJson(json, Array<Movie>::class.java).toList()
	}

}

