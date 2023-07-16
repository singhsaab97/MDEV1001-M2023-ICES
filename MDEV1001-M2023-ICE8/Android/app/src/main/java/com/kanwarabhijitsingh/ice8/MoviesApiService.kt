package com.kanwarabhijitsingh.ice8

import retrofit2.http.GET

interface MoviesApiService {
	@GET("api/list")
	suspend fun getMovies(): List<Movie>
}
