package com.fit.movies.data.datasource

import com.fit.movies.data.db.CategoryDao
import com.fit.movies.data.mappers.toDomainModel
import com.fit.movies.data.mappers.toEntityModel
import com.fit.movies.domain.datasource.LocalMovieDataSource
import com.fit.movies.domain.model.CategoryModel
import com.fit.movies.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalMovieDataSourceImp @Inject constructor(private val dao: CategoryDao): LocalMovieDataSource {
    override suspend fun getAllMovies(): Flow<List<CategoryModel>> {
        return dao.getAllCategoriesWithMovies().map { it.toDomainModel() }
    }

    override suspend fun insertCategory(category: CategoryModel) {
        return dao.insertCategory(category.toEntityModel())
    }

    override suspend fun insertMovie(categoryId: Int, movieModelList: List<MovieModel>) {
        return dao.insertMovie(movieModelList.toEntityModel(categoryId))
    }

    override suspend fun deleteAllMovies() {
        dao.deleteAllMovies()
        dao.deleteAllCategories()
    }
}