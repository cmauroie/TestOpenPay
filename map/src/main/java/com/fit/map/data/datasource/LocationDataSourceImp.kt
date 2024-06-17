package com.fit.map.data.datasource

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.fit.map.domain.datasource.LocationDataSource
import com.fit.map.extension.hasLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationDataSourceImp @Inject constructor(
    private val locationProviderClient: FusedLocationProviderClient,
    private val locationRequest: LocationRequest,
    private val context: Context
) : LocationDataSource {

    private lateinit var locationCallback: LocationCallback

    override fun getLocationUpdates(): Flow<Location> {
        return callbackFlow {
            if (!context.hasLocationPermission()) {
                throw LocationDataSource.LocationException("Missing location permission")
            }

            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                throw LocationDataSource.LocationException("GPS is disabled")
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        launch { send(location) }
                    }
                }
            }

            locationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose {
                locationProviderClient.removeLocationUpdates(locationCallback)
            }
        }
    }

    override fun stopLocationUpdates() {
        if (::locationCallback.isInitialized) {
            locationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

}