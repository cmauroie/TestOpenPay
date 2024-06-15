package com.fit.movies.domain.datasource

import com.fit.core.server.Resource
import com.fit.movies.domain.model.CategoryModel

interface RemoteMovieDataSource {
    suspend fun getPopularMovies(): Resource<CategoryModel>
    suspend fun getTopRatedMovies(): Resource<CategoryModel>
    suspend fun getRecommendationsMovies(): Resource<CategoryModel>
}