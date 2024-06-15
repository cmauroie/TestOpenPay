package com.fit.popularperson.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.popularperson.domain.usecase.GetPopularPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularPersonViewModel @Inject constructor(private val useCase: GetPopularPersonUseCase) : ViewModel() {
    private val TAG = PopularPersonViewModel::class.java.simpleName

    fun test(){
        viewModelScope.launch {

            Log.i(TAG,"USECASE = ${useCase()}")
        }
    }
}