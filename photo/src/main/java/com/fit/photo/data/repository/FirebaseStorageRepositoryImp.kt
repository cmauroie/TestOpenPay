package com.fit.photo.data.repository

import com.fit.photo.domain.datasource.FirebaseStorageDataSource
import com.fit.photo.domain.model.PhotoModel
import com.fit.photo.domain.repository.FirebaseStorageRepository
import javax.inject.Inject

class FirebaseStorageRepositoryImp @Inject constructor(private val dataSource: FirebaseStorageDataSource) :
    FirebaseStorageRepository {
    override suspend fun savePhoto(
        photoModel: PhotoModel,
        onSuccess: ((String) -> Unit)?,
        onFailure: (() -> Unit)?
    ) {
        dataSource.savePhoto(photoModel, onSuccess, onFailure)
    }
}