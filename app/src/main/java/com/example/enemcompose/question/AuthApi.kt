package com.example.enemcompose.question

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("users")
    suspend fun createUser(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("auth/login")
    suspend fun login(@Body requestBody: RequestBody): Response<ResponseBody>
}