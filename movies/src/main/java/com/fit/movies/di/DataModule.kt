package com.fit.movies.di

import com.fit.movies.data.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideSettingsApi(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

}