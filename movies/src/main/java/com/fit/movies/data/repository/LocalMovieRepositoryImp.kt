package com.fit.movies.data.repository

import com.fit.movies.domain.datasource.LocalMovieDataSource
import com.fit.movies.domain.model.CategoryModel
import com.fit.movies.domain.repository.LocalMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMovieRepositoryImp @Inject constructor(private val dataSource: LocalMovieDataSource) :
    LocalMovieRepository {
    override suspend fun getAllMovies(): Flow<List<CategoryModel>> {
        return dataSource.getAllMovies()
    }

    override suspend fun insertCategory(category: CategoryModel) {
        dataSource.insertCategory(category)
        dataSource.insertMovie(category.id, category.movies)
    }

    override suspend fun clearDatabase() {
        dataSource.deleteAllMovies()
    }
}