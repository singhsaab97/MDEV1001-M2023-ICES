package com.kanwarabhijitsingh.ice8

import retrofit2.http.*

interface MoviesApiService {
	@GET("api/list")
	suspend fun getMovies(): List<Movie>

	@POST("api/add")
	suspend fun addMovie(@Body movie: Movie)

	@PUT("api/update/{id}")
	suspend fun updateMovie(@Path("id") movieId: String, @Body movie: Movie)

	@DELETE("api/delete/{id}")
	suspend fun deleteMovie(@Path("id") movieId: String)
}
