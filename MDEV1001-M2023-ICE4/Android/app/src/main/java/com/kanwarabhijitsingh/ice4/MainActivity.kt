package com.kanwarabhijitsingh.ice4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import java.io.IOException
import java.nio.charset.Charset
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

		// Setup database
		val database = Room.databaseBuilder(this, MovieDatabase::class.java, "movie_database")
			.allowMainThreadQueries()
			.build()
		// Fetch movies
		val movies = parseJsonFileToMovies(database)
		movies.forEach {
			database.movieDao().insertMovie(it)
		}
		val savedMovies = database.movieDao().getAllMovies()
		// Init recycler view
		val recyclerView = findViewById<RecyclerView>(R.id.moviesRecyclerView)
		recyclerView.apply {
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = MoviesAdapter(movies)
		}
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
		val list = gson.fromJson(json, Array<Movie>::class.java).toList()
		val savedMovies = db.movieDao().getAllMovies()
		if (list.any { it in savedMovies }) {
			return emptyList()
		}
		return list
	}

}
