package com.fit.core.di

import android.content.Context
import com.google.firebase.FirebaseApp
//import com.google.firebase.appcheck.FirebaseAppCheck
//import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModuleCore {
    @Singleton
    @Provides
    fun providerFirebaseApp(@ApplicationContext context: Context): FirebaseApp {
        return FirebaseApp.initializeApp(context) ?: throw IllegalStateException("FirebaseApp initialization failed")
    }

    /*@Singleton
    @Provides
    fun providerFirebaseAppCheck(debugAppCheckProviderFactory:DebugAppCheckProviderFactory): FirebaseAppCheck {
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(debugAppCheckProviderFactory)
        return firebaseAppCheck
    }

    @Singleton
    @Provides
    fun providerDebugAppCheck(): DebugAppCheckProviderFactory {
        return DebugAppCheckProviderFactory.getInstance()
    }*/
}