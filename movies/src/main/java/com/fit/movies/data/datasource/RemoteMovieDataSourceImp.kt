package com.fit.movies.data.datasource

import com.fit.core.server.Resource
import com.fit.core.server.SafeApiCall
import com.fit.movies.data.mappers.toDomainModel
import com.fit.movies.data.network.ApiClient
import com.fit.movies.domain.datasource.RemoteMovieDataSource
import com.fit.movies.domain.enum.MovieCategory
import com.fit.movies.domain.model.CategoryModel
import javax.inject.Inject

class RemoteMovieDataSourceImp @Inject constructor(private val apiClient: ApiClient): RemoteMovieDataSource,
    SafeApiCall {
    override suspend fun getPopularMovies(): Resource<CategoryModel> {
        return safeApiCall {
            apiClient.getPopularMovies().toDomainModel(MovieCategory.POPULAR.path)
        }
    }

    override suspend fun getTopRatedMovies(): Resource<CategoryModel> {
        return safeApiCall {
            apiClient.getTopRatedMovies().toDomainModel(MovieCategory.TOP_RATED.path)
        }
    }

    override suspend fun getRecommendationsMovies(): Resource<CategoryModel> {
        return safeApiCall {
            apiClient.getRecommendations().toDomainModel(MovieCategory.RECOMMENDED.path)
        }
    }
}