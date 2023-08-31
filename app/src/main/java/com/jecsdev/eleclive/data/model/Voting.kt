package com.jecsdev.eleclive.data.model

import com.google.gson.annotations.SerializedName

data class Voting(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("vatId") var vatId: String,
    @SerializedName("census") var census: String,
    @SerializedName("school") var school: String
    )
