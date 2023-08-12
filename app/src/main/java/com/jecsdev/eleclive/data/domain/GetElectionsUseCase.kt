package com.jecsdev.eleclive.data.domain
import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.core.network.ApiService
import com.jecsdev.eleclive.data.model.Elections
import javax.inject.Inject

class GetElectionsUseCase @Inject constructor(private val apiService: ApiService) {
    suspend fun getElections(): ApiResponse<List<Elections>?> {
        return try {
            val response = apiService.getElections().execute()
            if (response.isSuccessful) {
                ApiResponse.Successful(response.code(), response.message(), response.body())
            } else {
                ApiResponse.Error(response.code(), response.message())
            }
        } catch (exception: Exception) {
            ApiResponse.Error(-1, exception.message.toString())
        }
    }
}