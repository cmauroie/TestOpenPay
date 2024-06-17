package com.fit.map.di

import android.content.Context
import com.fit.map.data.datasource.LocationDataSourceImp
import com.fit.map.data.repository.LocationRepositoryImp
import com.fit.map.domain.LocationRepository
import com.fit.map.domain.datasource.LocationDataSource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Singleton
    @Provides
    fun provideFusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun providerLocationRequest(): LocationRequest {
        return LocationRequest.Builder(300000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setWaitForAccurateLocation(true)
            .build()
    }

    @Singleton
    @Provides
    fun providerLocationDataSource(
        locationProviderClient: FusedLocationProviderClient,
        locationRequest: LocationRequest,
        @ApplicationContext context: Context
    ): LocationDataSource {
        return LocationDataSourceImp(locationProviderClient, locationRequest, context)
    }

    @Singleton
    @Provides
    fun providerLocationRepository(
        locationDataSource: LocationDataSource
    ): LocationRepository {
        return LocationRepositoryImp(locationDataSource)
    }

}