package com.kanwarabhijitsingh.ice7

import retrofit2.http.GET

interface MoviesApiService {
	@GET("api/list")
	suspend fun getMovies(): List<Movie>
}
