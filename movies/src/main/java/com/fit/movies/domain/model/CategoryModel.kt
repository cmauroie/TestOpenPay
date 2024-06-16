package com.fit.movies.domain.model

data class CategoryModel(
    val id: Int,
    val name: String,
    val movies: List<MovieModel>
)
