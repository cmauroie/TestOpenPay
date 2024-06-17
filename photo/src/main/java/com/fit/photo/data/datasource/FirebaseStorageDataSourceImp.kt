package com.fit.photo.data.datasource

import android.util.Log
import com.fit.photo.domain.datasource.FirebaseStorageDataSource
import com.fit.photo.domain.model.PhotoModel
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class FirebaseStorageDataSourceImp @Inject constructor(private val firebaseStorage: FirebaseStorage) :
    FirebaseStorageDataSource {

    override suspend fun savePhoto(
        photoModel: PhotoModel,
        onSuccess: ((String) -> Unit)?,
        onFailure: (() -> Unit)?
    ) {
        val storageRef =
            firebaseStorage.reference.child(photoModel.path).child("photo_${photoModel.name}.jpg")
        val uploadTask = storageRef.putBytes(photoModel.data)
        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                Log.i("DataSourceImp", downloadUrl)
                onSuccess?.invoke(downloadUrl)
            }.addOnFailureListener {
                onFailure?.invoke()
            }
        }
    }
    
}
