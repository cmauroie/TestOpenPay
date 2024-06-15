package com.fit.popularperson.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.popularperson.domain.model.ProfileModel
import com.fit.popularperson.domain.usecase.GetPopularPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularPersonViewModel @Inject constructor(private val useCase: GetPopularPersonUseCase) :
    ViewModel() {

    private val _uiModel = MutableStateFlow<UIModel>(UIModel.Loading)
    val uiModel: StateFlow<UIModel> = _uiModel

    init {
        fetchMovies()
    }

    sealed class UIModel {
        data object Loading : UIModel()
        class ShowView(val profileModel: ProfileModel) : UIModel()
        data object NoConnection : UIModel()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _uiModel.value = UIModel.Loading
            useCase()?.let {
                _uiModel.value = UIModel.ShowView(it)
            } ?: run {
                _uiModel.value = UIModel.NoConnection
            }
        }
    }
}