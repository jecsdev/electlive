package com.jecsdev.eleclive.domain.useCases
import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.domain.repository.Repository
import com.jecsdev.eleclive.data.model.Elections
import com.jecsdev.eleclive.data.model.Voting
import retrofit2.Response
import javax.inject.Inject

class ElectionsUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(): ApiResponse<List<Elections>>{
        return repository.getElections()
    }

    suspend fun createVoting(voting: Voting): Response<Voting> {
         return repository.createVoting(voting)
    }

}