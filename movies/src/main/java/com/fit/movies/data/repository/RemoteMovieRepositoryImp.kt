package com.fit.movies.data.repository

import com.fit.core.server.Resource
import com.fit.movies.domain.datasource.RemoteMovieDataSource
import com.fit.movies.domain.model.CategoryModel
import com.fit.movies.domain.repository.RemoteMovieRepository
import javax.inject.Inject

class RemoteMovieRepositoryImp @Inject constructor(private val dataSource: RemoteMovieDataSource): RemoteMovieRepository {
    override suspend fun getPopularMovies(): Resource<CategoryModel> {
        return dataSource.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): Resource<CategoryModel> {
        return dataSource.getTopRatedMovies()
    }

    override suspend fun getRecommendationsMovies(): Resource<CategoryModel> {
        return dataSource.getRecommendationsMovies()
    }
}