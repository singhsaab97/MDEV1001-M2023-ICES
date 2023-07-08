package com.kanwarabhijitsingh.ice7

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesActivity : AppCompatActivity() {

	private lateinit var progressBar: ProgressBar
	private lateinit var movieRecyclerView: RecyclerView
	private lateinit var movieAdapter: MovieAdapter

	private val movies = mutableListOf<Movie>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_movies)

		progressBar = findViewById(R.id.progressBar)
		movieRecyclerView = findViewById(R.id.movieRecyclerView)

		movieAdapter = MovieAdapter(movies)
		movieRecyclerView.adapter = movieAdapter
		movieRecyclerView.layoutManager = LinearLayoutManager(this)

		fetchMovies()
	}

	private fun fetchMovies() {
		// Show progress bar
		progressBar.visibility = View.VISIBLE

		val service = RetrofitClient.moviesApiService
		CoroutineScope(Dispatchers.Main).launch {
			try {
				val response = withContext(Dispatchers.IO) {
					service.getMovies()
				}
				if (response.isNotEmpty()) {
					movies.clear()
					movies.addAll(response)
					movieAdapter.notifyDataSetChanged()
				} else {
					showToast("No movies found")
				}
			} catch (e: Exception) {
				showToast("Failed to fetch movies: ${e.message}")
			} finally {
				// Hide progress bar
				progressBar.visibility = View.GONE
			}
		}
	}

	private fun showToast(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
	}
}
