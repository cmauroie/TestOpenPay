package com.fit.map.data.datasource

import com.fit.map.data.mappers.toServerModel
import com.fit.map.domain.datasource.FirestoreDataSource
import com.fit.map.domain.model.LocationModel
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirestoreDataSourceImp @Inject constructor(private val firebaseFirestore: FirebaseFirestore) :
    FirestoreDataSource {

    override suspend fun addLocationFirestore(
        locationModel: LocationModel,
        onSuccess: ((LocationModel) -> Unit)?,
        onFailure: (() -> Unit)?
    ) {
        val collectionRef =
            firebaseFirestore.collection(locationModel.collectionName).document(locationModel.date)

        collectionRef
            .set(locationModel.toServerModel())
            .addOnSuccessListener {
                onSuccess?.invoke(locationModel)
            }
            .addOnFailureListener { e ->
                onFailure?.invoke()
            }
    }

}