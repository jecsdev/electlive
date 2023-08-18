package com.jecsdev.eleclive.domain.repository

import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.core.network.ApiService
import com.jecsdev.eleclive.data.model.Elections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getElections(): Flow<ApiResponse<Elections>> = flow {
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.getElections()
            }

            if (response.isSuccessful) {
                emit(ApiResponse.Successful(response.code(), response.message(), response.body()))
            } else {
                emit(ApiResponse.Error(response.code(), response.message()))
            }
        } catch (exception: Exception) {
            emit(ApiResponse.Error(-1, exception.message ?: ""))
        }
    }
}


