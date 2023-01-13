package com.example.enemcompose.view.model

import android.util.Log
import com.example.enemcompose.question.QuestionApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

fun teste() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://servicodados.ibge.gov.br/api/v1/")
        .build()

    // Create Service
    val service = retrofit.create(QuestionApi::class.java)

    CoroutineScope(Dispatchers.IO).launch {
        /*
         * For @Query: You need to replace the following line with val response = service.getEmployees(2)
         * For @Path: You need to replace the following line with val response = service.getEmployee(53)
         */

        // Do the GET request and get response
        val response = service.getQuestion()

        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {

                // Convert raw JSON to pretty JSON using GSON library
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(
                    JsonParser.parseString(
                        response.body()
                            ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                    )
                )

                Log.d("Pretty Printed JSON :", prettyJson)

            } else {

                Log.e("RETROFIT_ERROR", response.code().toString())

            }
        }
    }
}