package com.fit.map.domain.usecase

import android.location.Location
import com.fit.map.domain.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(private val repository: LocationRepository) {
    fun start(): Flow<Location> {
        return repository.getLocationUpdatesFromProvider()
    }

    fun stop() {
        repository.stopLocationUpdatesFromProvider()
    }
}