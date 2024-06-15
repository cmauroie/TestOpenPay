package com.fit.movies.domain.repository

import com.fit.core.server.Resource
import com.fit.movies.domain.model.CategoryModel

interface RemoteMovieRepository {
    suspend fun getPopularMovies(): Resource<CategoryModel>
    suspend fun getTopRatedMovies(): Resource<CategoryModel>
    suspend fun getRecommendationsMovies(): Resource<CategoryModel>
}