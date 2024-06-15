package com.fit.movies.data.network

import com.fit.core.LANGUAGE_MOVIE
import com.fit.core.MOVIE_ID
import com.fit.movies.data.dto.ResponseMoviesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("language") region: String = LANGUAGE_MOVIE) : ResponseMoviesDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("language") region: String = LANGUAGE_MOVIE) : ResponseMoviesDto

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendations(@Path("movie_id") movieId: String = MOVIE_ID) : ResponseMoviesDto
}