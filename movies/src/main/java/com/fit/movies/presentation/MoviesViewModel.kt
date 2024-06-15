package com.fit.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fit.movies.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val useCase: GetMoviesUseCase) : ViewModel() {

    private val TAG = MoviesViewModel::class.java.simpleName
    /*fun test() {
        viewModelScope.launch {
            val response = useCase.invoke()
            Log.i(TAG, "RESPONSE: $response")
        }
    }*/

    val allCategories = liveData(Dispatchers.IO) {
        val categories = useCase.invoke()
        emit(categories)
    }
}