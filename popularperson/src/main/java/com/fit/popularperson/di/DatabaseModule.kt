package com.fit.popularperson.di


import android.content.Context
import androidx.room.Room
import com.fit.popularperson.data.db.ProfileDao
import com.fit.popularperson.data.db.ProfileDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDispatchFormDao(database: ProfileDatabase): ProfileDao =
        database.profileDao()

    @Singleton
    @Provides
    fun provideDispatchFormDatabase(@ApplicationContext context: Context): ProfileDatabase {
        return Room.databaseBuilder(
            context,
            ProfileDatabase::class.java,
            name = "database-popular-people"
        ).fallbackToDestructiveMigration().build()
    }
}