package com.fit.movies.domain.datasource

import com.fit.movies.domain.model.CategoryModel
import com.fit.movies.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface LocalMovieDataSource {
    suspend fun getAllMovies(): Flow<List<CategoryModel>>

    suspend fun insertCategory(category: CategoryModel)

    suspend fun insertMovie(categoryId: Int, movieModelList: List<MovieModel>)
    suspend fun deleteAllMovies()
}