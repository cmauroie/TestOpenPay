package com.fit.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CategoryEntity::class, MovieEntity::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}