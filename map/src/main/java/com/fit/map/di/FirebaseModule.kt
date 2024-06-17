package com.fit.map.di

import android.content.Context
import com.fit.map.data.datasource.FirestoreDataSourceImp
import com.fit.map.data.repository.FirestoreRepositoryImp
import com.fit.map.domain.datasource.FirestoreDataSource
import com.fit.map.domain.repository.FirestoreRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun providerFirebaseApp(@ApplicationContext context: Context): FirebaseApp {
        return FirebaseApp.initializeApp(context) ?: throw IllegalStateException("FirebaseApp initialization failed")
    }

    @Singleton
    @Provides
    fun providerFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun providerFirestoreDataSource(firebaseFirestore: FirebaseFirestore): FirestoreDataSource {
        return FirestoreDataSourceImp(firebaseFirestore)
    }

    @Singleton
    @Provides
    fun providerFirestoreRepository(firestoreDataSource: FirestoreDataSource): FirestoreRepository {
        return FirestoreRepositoryImp(firestoreDataSource)
    }

}