package com.fit.popularperson.di

import com.fit.popularperson.data.network.ApiClient
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