package com.kanwarabhijitsingh.ice5

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
	@Query("SELECT * FROM Movie")
	fun getAllMovies(): List<Movie>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertMovie(movie: Movie)
}
