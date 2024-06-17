package com.fit.map.domain.datasource

import com.fit.map.domain.model.LocationModel

interface FirestoreDataSource {

    suspend fun addLocationFirestore(
        locationModel: LocationModel,
        onSuccess: ((LocationModel) -> Unit)? = null,
        onFailure: (() -> Unit)? = null
    )

}