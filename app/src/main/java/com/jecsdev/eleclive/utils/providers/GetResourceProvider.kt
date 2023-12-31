package com.jecsdev.eleclive.utils.providers

import android.content.Context

class GetResourceProvider(private val context: Context): ResourceProvider {
    override fun getString(resourceId: Int): String {
        return context.getString(resourceId)
    }
}