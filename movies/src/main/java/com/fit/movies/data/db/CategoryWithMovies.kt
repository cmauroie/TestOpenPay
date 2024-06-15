package com.fit.movies.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithMovies(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val movies: List<MovieEntity>
)
