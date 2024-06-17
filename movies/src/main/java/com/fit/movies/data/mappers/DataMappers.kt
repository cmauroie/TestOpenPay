package com.fit.movies.data.mappers

import com.fit.core.BuildConfig
import com.fit.movies.data.db.CategoryEntity
import com.fit.movies.data.db.CategoryWithMovies
import com.fit.movies.data.db.MovieEntity
import com.fit.movies.data.dto.ResponseMoviesDto
import com.fit.movies.data.dto.ResultDto
import com.fit.movies.domain.model.CategoryModel
import com.fit.movies.domain.model.MovieModel


fun ResponseMoviesDto.toDomainModel(categoryId: Int, categoryName: String): CategoryModel {
    return CategoryModel(categoryId, categoryName, this.results.map { it.toDomainModel() })
}

fun ResultDto.toDomainModel(): MovieModel {
    return MovieModel(
        this.id,
        this.title,
        "${BuildConfig.URL_BASE_IMAGE}${this.posterPath}",
        this.overview
    )
}

fun List<CategoryWithMovies>.toDomainModel(): List<CategoryModel> {
    return this.map { categoryWithMovies ->
        CategoryModel(
            id = categoryWithMovies.category.id,
            name = categoryWithMovies.category.name,
            movies = categoryWithMovies.movies.map { movieEntity ->
                MovieModel(
                    id = movieEntity.id,
                    title = movieEntity.title,
                    imageUrl = movieEntity.imageUrl,
                    description = movieEntity.description
                )
            }
        )
    }
}

fun List<CategoryModel>.toEntityModel():List<CategoryEntity>{
    return this.map {
        it.toEntityModel()
    }
}

fun CategoryModel.toEntityModel(): CategoryEntity {
    return CategoryEntity(
        this.id,
        this.name
    )
}

fun List<MovieModel>.toEntityModel(categoryId: Int): List<MovieEntity> {
    return this.map {
        MovieEntity(
            id = it.id,
            title = it.title,
            imageUrl = it.imageUrl,
            description = it.description,
            categoryId = categoryId
        )
    }
}