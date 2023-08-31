package com.jecsdev.eleclive.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.eleclive.core.network.PostResult
import com.jecsdev.eleclive.data.model.Voting
import com.jecsdev.eleclive.domain.useCases.ElectionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class represents the Barcode ViewModel
 * @param electionsUseCase handle the useCase for elections
 */
class BarcodeViewModel @Inject constructor(private val electionsUseCase: ElectionsUseCase) :
    ViewModel() {

    private val _votingState: MutableStateFlow<PostResult<Voting>> =
        MutableStateFlow(PostResult.Loading)
    val votingState: StateFlow<PostResult<Voting>> = _votingState

    /**
     * This function creates voting
     * @param voting is the voting model
     */

    fun createVoting(voting: Voting) {
        viewModelScope.launch {
            _votingState.value = PostResult.Loading
            val result = electionsUseCase.createVoting(voting)
            _votingState.value = result
        }
    }

}