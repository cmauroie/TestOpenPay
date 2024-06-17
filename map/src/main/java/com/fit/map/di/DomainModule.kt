package com.fit.map.di

import com.fit.map.domain.LocationRepository
import com.fit.map.domain.repository.FirestoreRepository
import com.fit.map.domain.usecase.GetLocationUseCase
import com.fit.map.domain.usecase.SendLocationFirestoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideGetLocationUpdatesUseCase(repository: LocationRepository): GetLocationUseCase {
        return GetLocationUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSendLocationFireStoreUseCase(repository: FirestoreRepository): SendLocationFirestoreUseCase {
        return SendLocationFirestoreUseCase(repository)
    }
}