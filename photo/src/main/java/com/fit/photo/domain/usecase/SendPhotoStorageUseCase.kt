package com.fit.photo.domain.usecase

import com.fit.photo.domain.model.PhotoModel
import com.fit.photo.domain.repository.FirebaseStorageRepository
import javax.inject.Inject

class SendPhotoStorageUseCase @Inject constructor(private val repository: FirebaseStorageRepository) {
    suspend operator fun invoke(
        photoModel: PhotoModel,
        onSuccess: ((String) -> Unit)? = null,
        onFailure: (() -> Unit)? = null
    ) {
        repository.savePhoto(photoModel, onSuccess, onFailure)
    }
}