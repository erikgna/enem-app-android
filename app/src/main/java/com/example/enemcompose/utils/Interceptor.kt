package com.example.enemcompose.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor(context: Context) : Interceptor {
    private val tokenManager: TokenManager = TokenManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${tokenManager.getToken()}").build()

        return chain.proceed(request)
    }
}