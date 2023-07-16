package com.kanwarabhijitsingh.ice8

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
	private const val BASE_URL = "https://mdev1001-m2023-api.onrender.com/"
	private const val TIMEOUT = 60L

	private val moshi = Moshi.Builder()
		.add(KotlinJsonAdapterFactory())
		.build()

	private val retrofit = Retrofit.Builder()
		.baseUrl(BASE_URL)
		.client(
			OkHttpClient.Builder()
				.connectTimeout(TIMEOUT, TimeUnit.SECONDS) // Increase timeout here
				.build()
		)
		.addConverterFactory(MoshiConverterFactory.create(moshi))
		.build()

	val moviesApiService: MoviesApiService = retrofit.create(MoviesApiService::class.java)
}
