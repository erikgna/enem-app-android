package com.example.enemcompose.question

import com.example.enemcompose.models.QuestionModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface QuestionApi {
    @GET("questions/{id}")
    suspend fun getQuestionById(@Path(value = "id", encoded = true) id: String): Response<ResponseBody>

    @POST("questions")
    suspend fun getQuestion(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("users/questions")
    suspend fun getUserQuestions(): Response<ResponseBody>

    @PATCH("users/new-question")
    suspend fun addNewQuestion(@Body requestBody: RequestBody): Response<ResponseBody>

    @DELETE("users/erase-history")
    suspend fun eraseHistory(): Response<ResponseBody>
}
