package com.jecsdev.eleclive.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.data.domain.GetElectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getElectionsUseCase: GetElectionsUseCase) : ViewModel() {

    fun getElectionsFromService() {
        viewModelScope.launch {
            when(val apiResponse = getElectionsUseCase.getElections()){
                is ApiResponse.Successful ->{
                    val data = apiResponse.data

                }
                is ApiResponse.Error -> {
                    val errorCode = apiResponse.code
                    val errorMessage = apiResponse.message
                }
            }
        }
    }
}