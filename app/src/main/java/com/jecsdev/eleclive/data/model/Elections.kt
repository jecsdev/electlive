package com.jecsdev.eleclive.data.model

import com.google.gson.annotations.SerializedName

data class Elections(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("date") var date: String
)
