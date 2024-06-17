package com.fit.map.data.repository

import com.fit.map.domain.datasource.FirestoreDataSource
import com.fit.map.domain.model.LocationModel
import com.fit.map.domain.repository.FirestoreRepository
import javax.inject.Inject

class FirestoreRepositoryImp @Inject constructor(private val dataSource: FirestoreDataSource) :
    FirestoreRepository {
    override suspend fun addLocationFirestore( location: LocationModel,
                                               onSuccess: ((LocationModel) -> Unit)?,
                                               onFailure: (() -> Unit)?) {
        dataSource.addLocationFirestore(location, onSuccess, onFailure)
    }
}