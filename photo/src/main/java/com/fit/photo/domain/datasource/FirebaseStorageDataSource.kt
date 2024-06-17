package com.fit.photo.domain.datasource

import com.fit.photo.domain.model.PhotoModel

interface FirebaseStorageDataSource {
    suspend fun savePhoto(
        photoModel: PhotoModel,
        onSuccess: ((String) -> Unit)? = null,
        onFailure: (() -> Unit)? = null
    )
}