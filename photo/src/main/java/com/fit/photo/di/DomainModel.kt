package com.fit.photo.di

import com.fit.photo.domain.repository.FirebaseStorageRepository
import com.fit.photo.domain.usecase.SendPhotoStorageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModel {
    @Singleton
    @Provides
    fun provideSendLocationFireStoreUseCase(repository: FirebaseStorageRepository): SendPhotoStorageUseCase {
        return SendPhotoStorageUseCase(repository)
    }
}