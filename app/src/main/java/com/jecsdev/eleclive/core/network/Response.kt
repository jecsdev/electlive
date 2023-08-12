package com.jecsdev.eleclive.core.network
sealed class ApiResponse<Elections> {
    data class Successful<Elections>(val code: Int, val message: String, val data: Elections) :
        ApiResponse<Elections>()

    data class Error<Elections>(val code: Int, val message: String) : ApiResponse<Elections>()
}
