package com.fit.movies.domain.repository

import com.fit.movies.domain.model.CategoryModel
import com.fit.movies.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface LocalMovieRepository {
    suspend fun getAllMovies(): Flow<List<CategoryModel>>

    suspend fun insertCategory(category: CategoryModel)

    suspend fun clearDatabase()
}