package com.fit.map.domain.datasource

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    fun getLocationUpdates(): Flow<Location>

    fun stopLocationUpdates()

    class LocationException(message: String): Exception()
}