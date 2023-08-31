package com.jecsdev.eleclive.core.network

import com.jecsdev.eleclive.data.model.Elections
import com.jecsdev.eleclive.data.model.Voting
import com.jecsdev.eleclive.utils.constants.NetworkConstants.ELECTIONS
import com.jecsdev.eleclive.utils.constants.NetworkConstants.VOTING
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    //Retrieves the elections available
    @GET(ELECTIONS)
    suspend fun getElections(): Response<List<Elections>>

    @POST(VOTING)
    suspend fun createVoting(@Body voting: Voting): Response<Voting>

}