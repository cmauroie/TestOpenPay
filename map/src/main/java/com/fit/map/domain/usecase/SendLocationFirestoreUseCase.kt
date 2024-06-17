package com.fit.map.domain.usecase

import com.fit.map.domain.model.LocationModel
import com.fit.map.domain.repository.FirestoreRepository
import javax.inject.Inject

class SendLocationFirestoreUseCase @Inject constructor(private val repository: FirestoreRepository) {
    suspend operator fun invoke(
        locationModel: LocationModel,
        onSuccess: ((LocationModel) -> Unit)? = null,
        onFailure: (() -> Unit)? = null
    ) {
        repository.addLocationFirestore(locationModel, onSuccess, onFailure)
    }
}