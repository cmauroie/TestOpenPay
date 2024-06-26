package com.fit.photo.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.photo.domain.model.PhotoModel
import com.fit.photo.domain.usecase.SendPhotoStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val useCase: SendPhotoStorageUseCase) :
    ViewModel() {

    private val _uiModel = MutableLiveData<UIModel>(UIModel.Loading)
    val uiModel: LiveData<UIModel> = _uiModel

    sealed class UIModel {
        data object Loading : UIModel()
        class ShowView(val photoUrl: String) : UIModel()
        class ShowCountImage(val listUri: List<Uri>) : UIModel()
        data object NoConnection : UIModel()
    }

    fun sendPhotoToFirestorage(byteArray: ByteArray) {
        val timestamp = System.currentTimeMillis()
        val request = PhotoModel(
            "Images",
            "$timestamp",
            byteArray
        )
        viewModelScope.launch {
            useCase.invoke(request, onSuccess = {
                _uiModel.postValue(UIModel.ShowView(it))
            }, onFailure = {
                _uiModel.value = UIModel.NoConnection
            })
        }
    }

    fun updateImageUris(uris: List<Uri>) {
        _uiModel.value = UIModel.ShowCountImage(uris)
    }

}