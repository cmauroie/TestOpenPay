package com.fit.photo.di

import com.fit.photo.data.datasource.FirebaseStorageDataSourceImp
import com.fit.photo.data.repository.FirebaseStorageRepositoryImp
import com.fit.photo.domain.datasource.FirebaseStorageDataSource
import com.fit.photo.domain.repository.FirebaseStorageRepository
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun providerFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun providerFirebaseStorageDataSource(firebaseFirestore: FirebaseStorage): FirebaseStorageDataSource {
        return FirebaseStorageDataSourceImp(firebaseFirestore)
    }

    @Singleton
    @Provides
    fun providerFirebaseStorageRepository(firebaseStorageDataSource: FirebaseStorageDataSource): FirebaseStorageRepository {
        return FirebaseStorageRepositoryImp(firebaseStorageDataSource)
    }
}