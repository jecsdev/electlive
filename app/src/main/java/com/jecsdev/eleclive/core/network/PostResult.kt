package com.jecsdev.eleclive.core.network

import com.jecsdev.eleclive.data.model.Voting

sealed class PostResult<Voting>{
    data class Successful(val code: Int, val message: String, val data: Voting): PostResult<Voting>()
    data class Error(val code: Int, val message: String): PostResult<Voting>()
    object  Loading: PostResult<Voting>()
}
