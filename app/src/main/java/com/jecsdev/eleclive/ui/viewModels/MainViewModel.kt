package com.jecsdev.eleclive.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.domain.useCases.ElectionsUseCase
import com.jecsdev.eleclive.data.model.Elections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getElectionsUseCase: ElectionsUseCase
) :
    ViewModel() {

    private val _electionsFlow = MutableStateFlow<ApiResponse<List<Elections>>>(ApiResponse.Loading)
    val electionsFlow: Flow<ApiResponse<List<Elections>>> = _electionsFlow

    init{
        getElections()
    }

    private fun getElections() {
        viewModelScope.launch {
            _electionsFlow.value = ApiResponse.Loading

            try {
                val response = getElectionsUseCase()

                _electionsFlow.value = response
            } catch (e: Exception) {
                _electionsFlow.value = ApiResponse.Error(-1, e.message.toString())
            }
        }
    }
}