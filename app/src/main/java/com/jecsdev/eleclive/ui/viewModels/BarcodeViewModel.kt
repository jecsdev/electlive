package com.jecsdev.eleclive.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.eleclive.data.model.Voting
import com.jecsdev.eleclive.domain.useCases.ElectionsUseCase
import com.jecsdev.eleclive.utils.constants.NetworkConstants.RESPONSE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 * This class represents the Barcode ViewModel
 * @param electionsUseCase handle the useCase for elections
 */
@HiltViewModel
class BarcodeViewModel @Inject constructor(private val electionsUseCase: ElectionsUseCase) :
    ViewModel() {

    // This variable handles the state from voting request
    private val _votingState = MutableStateFlow<Response<Unit>>(Response.success(Unit))

    /**
     * This function creates voting
     * @param voting is the voting model
     */

    fun createVoting(voting: Voting) {
        viewModelScope.launch {
            val response = electionsUseCase.createVoting(voting)
            _votingState.value = response
            Log.i(RESPONSE, _votingState.value.code().toString())
        }
    }
}


