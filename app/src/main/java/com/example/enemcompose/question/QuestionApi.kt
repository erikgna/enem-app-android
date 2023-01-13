package com.example.enemcompose.question
import com.example.enemcompose.question.QuestionModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface QuestionApi {
    @GET("localidades/estados/33/municipios")
    suspend fun getQuestion(): Response<ResponseBody>
}
