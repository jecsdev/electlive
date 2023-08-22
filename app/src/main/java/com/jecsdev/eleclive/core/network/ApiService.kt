package com.jecsdev.eleclive.core.network

import com.jecsdev.eleclive.data.model.Elections
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    //Retrieves the elections available
    @GET("Elections")
    suspend fun getElections(): Response<List<Elections>>
}