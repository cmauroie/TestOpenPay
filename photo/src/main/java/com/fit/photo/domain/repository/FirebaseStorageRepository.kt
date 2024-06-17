package com.fit.photo.domain.repository

import com.fit.photo.domain.model.PhotoModel

interface FirebaseStorageRepository {
    suspend fun savePhoto(
        photoModel: PhotoModel,
        onSuccess: ((String) -> Unit)? = null,
        onFailure: (() -> Unit)? = null
    )
}