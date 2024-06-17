package com.fit.map.domain.repository

import com.fit.map.domain.model.LocationModel

interface FirestoreRepository {
    suspend fun addLocationFirestore(location: LocationModel, onSuccess: ((LocationModel) -> Unit)? = null, onFailure: (() -> Unit)? = null)
}