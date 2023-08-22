package com.jecsdev.eleclive.domain.repository

import android.util.Log
import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.core.network.ApiService
import com.jecsdev.eleclive.data.model.Elections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
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
        }catch (e: Exception){
            ApiResponse.Error(-1, e.message.toString())
        }
    }
}



