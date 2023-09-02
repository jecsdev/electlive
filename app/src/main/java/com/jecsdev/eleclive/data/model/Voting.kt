package com.jecsdev.eleclive.data.model

import com.google.gson.annotations.SerializedName

/**
 * This class represents the Model Voting to be sent
 */
data class Voting(
    @SerializedName("name") var name: String,
    @SerializedName("vatId") var vatId: String,
    @SerializedName("census") var census: String,
    @SerializedName("school") var school: String
)
