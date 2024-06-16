package com.fit.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.movies.domain.model.CategoryModel
import com.fit.movies.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val useCase: GetMoviesUseCase) : ViewModel() {

    private val _uiModel = MutableStateFlow<UIModel>(UIModel.Loading)
    val uiModel: StateFlow<UIModel> = _uiModel

    init {
        fetchMovies()
    }

    sealed class UIModel {
        data object Loading : UIModel()
        class ShowView(val categoryModel: List<CategoryModel>) : UIModel()
        data object NoConnection : UIModel()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _uiModel.value = UIModel.Loading
            useCase().let {
                if (it.isNotEmpty()) {
                    _uiModel.value = UIModel.ShowView(it)
                } else {
                    _uiModel.value = UIModel.NoConnection
                }
            }
        }
    }
}