package com.example.enemcompose.question

import retrofit2.Response
import javax.inject.Inject

class QuestionRepo @Inject constructor(
    private val questionApi: QuestionApi
) {
    suspend fun getQuestion() {
        val teste = questionApi.getQuestion()
        println(teste)
        //return teste.body()
    }
}