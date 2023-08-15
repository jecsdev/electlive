package com.jecsdev.eleclive.domain.useCases
import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.domain.Repository.Repository
import com.jecsdev.eleclive.data.model.Elections
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetElectionsUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<ApiResponse<Elections>> {
        return repository.getElections()
    }

}