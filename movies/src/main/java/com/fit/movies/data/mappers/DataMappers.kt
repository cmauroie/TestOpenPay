package com.fit.movies.data.mappers

import com.fit.core.URL_BASE_IMAGE
import com.fit.movies.data.dto.ResponseMoviesDto
import com.fit.movies.data.dto.ResultDto
import com.fit.movies.domain.model.CategoryModel
import com.fit.movies.domain.model.MovieModel


fun ResponseMoviesDto.toDomainModel(category: String): CategoryModel {
    return CategoryModel(category, this.results.map { it.toDomainModel() } )
}

fun ResultDto.toDomainModel(): MovieModel{
    return MovieModel(
        this.id,
        this.title,
        "$URL_BASE_IMAGE${this.posterPath}",
        this.overview
    )
}