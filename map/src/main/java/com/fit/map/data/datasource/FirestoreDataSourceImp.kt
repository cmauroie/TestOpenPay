package com.fit.map.data.datasource

import com.fit.map.domain.datasource.FirestoreDataSource
import com.fit.map.domain.model.LocationModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
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

        val datos = Datos(
            GeoPoint(locationModel.latitude, locationModel.longitude),
            locationModel.latitude,
            locationModel.longitude
        )

        collectionRef
            .set(datos)
            .addOnSuccessListener {
                onSuccess?.invoke(locationModel)
            }
            .addOnFailureListener { e ->
                onFailure?.invoke()
            }
    }

    data class Datos(
        val geoPoint: GeoPoint,
        val latitude: Double,
        val longitude: Double
    )

}