package com.fit.popularperson.data.network

import com.fit.core.BuildConfig

import com.fit.popularperson.data.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("person/popular")
    suspend fun getPopularPeople(@Query("language") region: String = BuildConfig.LANGUAGE_MOVIE) : ResponseDto
}