package com.jecsdev.eleclive.data.domain.UseCases
import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.data.domain.Repository.Repository
import com.jecsdev.eleclive.data.model.Elections
import javax.inject.Inject

class GetElectionsUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(): ApiResponse<List<Elections>?> {
        return repository.getElections()
    }

}