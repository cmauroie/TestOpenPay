package com.fit.map.data.repository

import android.location.Location
import com.fit.map.domain.LocationRepository
import com.fit.map.domain.datasource.LocationDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImp @Inject constructor(private val dataSource: LocationDataSource):
    LocationRepository {
    override fun getLocationUpdatesFromProvider(): Flow<Location> {
        return dataSource.getLocationUpdates()
    }

    override fun stopLocationUpdatesFromProvider() {
        return dataSource.stopLocationUpdates()
    }
}