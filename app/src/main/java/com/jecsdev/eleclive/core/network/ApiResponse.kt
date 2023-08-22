package com.jecsdev.eleclive.core.network

import com.jecsdev.eleclive.data.model.Elections


sealed class ApiResponse<Elections> {
    data class Successful(val code: Int, val message: String, val data: List<Elections>?) :
        ApiResponse<List<Elections>>()

    data class Error(val code: Int, val message: String) :ApiResponse<List<Elections>>()

    object Loading:ApiResponse<List<Elections>>()
}
