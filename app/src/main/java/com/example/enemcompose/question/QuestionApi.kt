package com.example.enemcompose.question
import com.example.enemcompose.models.QuestionModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QuestionApi {
    @POST("questions")
    suspend fun getQuestion(@Body requestBody: RequestBody): Response<ResponseBody>
}
