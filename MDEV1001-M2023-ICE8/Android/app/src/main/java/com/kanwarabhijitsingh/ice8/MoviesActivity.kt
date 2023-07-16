package com.kanwarabhijitsingh.ice8

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

		movieRecyclerView.apply {
			movieAdapter?.setDeleteActionListener { movie ->
				val builder = AlertDialog.Builder(this@MoviesActivity)
				builder.setTitle("Delete " + movie.title + "?")
				builder.setMessage("This action will delete it from the database permanently")
				builder.setPositiveButton("Delete") { alert, _ ->
					val service = RetrofitClient.moviesApiService
					CoroutineScope(Dispatchers.Main).launch {
						try {
							withContext(Dispatchers.IO) {
								service.deleteMovie(movie.id)
							}
						} catch (e: Exception) {
							showToast("Failed to delete movie: ${e.message}")
						} finally {
							fetchMovies()
						}
					}
					alert.dismiss()
				}
				builder.setNegativeButton("Cancel") { alert, _ ->
					alert.dismiss()
				}
				builder.create().show()
			}

			movieAdapter?.setEditActionListener {
				val intent = Intent(this@MoviesActivity, AddEditMovieActivity::class.java)
				intent.putExtra("Data", it)
				startActivity(intent)
			}
		}

		fetchMovies()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.menu_movies, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.addButton -> {
				val intent = Intent(this, AddEditMovieActivity::class.java)
				startActivity(intent)
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onResume() {
		super.onResume()
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
