package com.kanwarabhijitsingh.ice5

import androidx.room.*

@Dao
interface MovieDao {
	@Query("SELECT * FROM Movie")
	fun getAllMovies(): List<Movie>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addMovie(movie: Movie)

	@Update
	fun updateMovie(movie: Movie)

	@Delete
	fun deleteMovie(movie: Movie)
}
