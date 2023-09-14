package com.jecsdev.eleclive.domain.repository

import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.core.network.ApiService
import com.jecsdev.eleclive.data.model.Elections
import com.jecsdev.eleclive.data.model.Voting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

/**
 * This class represents the App Repository
 * @param apiService handle all the app endpoints
 */

class Repository @Inject constructor(private val apiService: ApiService) {

    /**
     * Retrieves all the elections available
     */
    suspend fun getElections(): ApiResponse<List<Elections>> {
        return try {
            val response = withContext(Dispatchers.IO) {
                apiService.getElections()
            }

            if (response.isSuccessful) {
                ApiResponse.Successful(response.code(), response.message(), response.body())
            } else {
                ApiResponse.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiResponse.Error(-1, e.message.toString())
        }
    }

    /**
     *  Create new voter for handle his vatId
     *  @param voting is the voter object
     */
    suspend fun createVoting(voting: Voting): Response<Unit> {
        return apiService.createVoting(voting)
    }

}



