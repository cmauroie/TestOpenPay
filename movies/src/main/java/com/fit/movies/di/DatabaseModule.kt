package com.fit.movies.di

import android.content.Context
import androidx.room.Room
import com.fit.movies.data.db.CategoryDao
import com.fit.movies.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDispatchFormDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            name = "database-movie"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDispatchFormDao(database: MovieDatabase): CategoryDao =
        database.categoryDao()
}