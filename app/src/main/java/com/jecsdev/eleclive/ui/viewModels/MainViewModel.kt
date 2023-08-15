package com.jecsdev.eleclive.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.eleclive.R
import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.utils.GetResourceProvider
import com.jecsdev.eleclive.domain.useCases.GetElectionsUseCase
import com.jecsdev.eleclive.data.model.Elections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getElectionsUseCase: GetElectionsUseCase,
    private val getResourceProvider: GetResourceProvider
) :
    ViewModel() {

    // Represents the loading state flow
    private val _loadingStateFlow = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingStateFlow

    // Represents the data sate flow received from API
    private val _dataStateFlow = MutableStateFlow<List<Elections>>(emptyList())
    val dataStateFlow: StateFlow<List<Elections>> = _dataStateFlow

    // Represents the response code returned from API
    private val _responseCodeStateFlow = MutableStateFlow(0)
    val responseCodeStateFlow: StateFlow<Int> = _responseCodeStateFlow

    //Represents the message response from API
    private val _responseMessageStateFlow = MutableStateFlow(getResourceProvider.getString(R.string.empty_string))
    val responseMessageStateFlow: StateFlow<String> = _responseMessageStateFlow
    fun getElectionsFromService() {
        viewModelScope.launch {
            _loadingStateFlow.value = true

            getElectionsUseCase().collect { apiResponse ->
                when (apiResponse) {
                    is ApiResponse.Successful -> {
                        if (apiResponse.data != null) {
                            delay(2000)
                            _loadingStateFlow.value = false
                            _dataStateFlow.value = apiResponse.data
                            _responseCodeStateFlow.value = apiResponse.code
                            _responseMessageStateFlow.value = apiResponse.message

                        }
                    }

                    is ApiResponse.Error -> {
                        _loadingStateFlow.value = false
                    }

                    is ApiResponse.Loading -> {

                    }
                }
            }
        }
    }
}