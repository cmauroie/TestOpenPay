package com.fit.popularperson.data.network

import com.fit.core.LANGUAGE_MOVIE
import com.fit.core.MOVIE_API_KEY
import com.fit.popularperson.data.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("person/popular")
    suspend fun getpopularpeoples(@Query("api_key") apiKey: String = MOVIE_API_KEY,
                                   @Query("language") region: String = LANGUAGE_MOVIE) : ResponseDto
}