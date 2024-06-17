package com.fit.map.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.map.domain.model.LocationModel
import com.fit.map.domain.usecase.SendLocationFirestoreUseCase
import com.fit.map.utils.getCurrentDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val useCase: SendLocationFirestoreUseCase) :
    ViewModel() {

    private val _uiModel = MutableLiveData<UIModel>(UIModel.Loading)
    val uiModel: LiveData<UIModel> = _uiModel


    sealed class UIModel {
        data object Loading : UIModel()
        class ShowView(val locationModel: LocationModel) : UIModel()
        data object NoConnection : UIModel()
    }

    fun sendLocationFirestore(latitude: Double, longitude: Double) {
        val request = LocationModel(
            "Location",
            getCurrentDateString(),
            latitude,
            longitude
        )
        viewModelScope.launch {
            useCase.invoke(request, onSuccess = {
                _uiModel.postValue(UIModel.ShowView(it))
            }, onFailure = {
                _uiModel.value = UIModel.NoConnection
            })
        }
    }
}