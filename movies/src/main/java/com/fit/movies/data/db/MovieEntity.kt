package com.fit.movies.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String,
    val description: String,
    val categoryId: Int
)
