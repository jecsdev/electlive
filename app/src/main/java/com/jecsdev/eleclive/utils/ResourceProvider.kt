package com.jecsdev.eleclive.utils

interface ResourceProvider {
    fun getString(resourceId: Int): String
}