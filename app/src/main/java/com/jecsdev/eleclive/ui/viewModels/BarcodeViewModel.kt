package com.jecsdev.eleclive.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.eleclive.R
import com.jecsdev.eleclive.data.model.Voting
import com.jecsdev.eleclive.domain.useCases.ElectionsUseCase
import com.jecsdev.eleclive.utils.providers.GetResourceProvider
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
class BarcodeViewModel @Inject constructor(private val electionsUseCase: ElectionsUseCase,
    private val resourceProvider: GetResourceProvider) :
    ViewModel() {
    private val emptyString = resourceProvider.getString(R.string.empty_string)
    private val _votingState =
        MutableStateFlow<Response<Voting>>(Response.success(Voting(emptyString, emptyString, emptyString, emptyString)))

    /**
     * This function creates voting
     * @param voting is the voting model
     */

    fun createVoting(voting: Voting) {
        viewModelScope.launch {
            val result = electionsUseCase.createVoting(voting)
            _votingState.value = result
        }
    }
}


