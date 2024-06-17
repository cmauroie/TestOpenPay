package com.fit.map.data.dto

import com.google.firebase.firestore.GeoPoint

data class DataLocationDto(
    val geoPoint: GeoPoint,
    val latitude: Double,
    val longitude: Double
)
