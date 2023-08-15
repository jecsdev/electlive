package com.jecsdev.eleclive.domain.Repository

import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.core.network.ApiService
import com.jecsdev.eleclive.data.model.Elections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    fun getElections(): Flow<ApiResponse<Elections>> = flow {
        val response = apiService.getElections().execute()
        if (response.isSuccessful) {
            emit(ApiResponse.Successful(response.code(), response.message(), response.body()))
        } else {
            emit(ApiResponse.Error(response.code(), response.message()))
        }
    }.catch { exception ->
        emit(ApiResponse.Error(-1, exception.message.toString()))
    }.flowOn(Dispatchers.IO)
}
