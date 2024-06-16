package com.fit.map.domain

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocationUpdatesFromProvider(): Flow<Location>

    fun stopLocationUpdatesFromProvider()
}