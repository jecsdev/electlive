package com.jecsdev.eleclive.utils.providers

interface ResourceProvider {
    fun getString(resourceId: Int): String
}