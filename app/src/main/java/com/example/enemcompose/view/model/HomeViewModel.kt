package com.example.enemcompose.view.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enemcompose.Constants
import com.example.enemcompose.models.QuestionModel
import com.example.enemcompose.question.QuestionApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.internal.wait
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.random.Random

class QuestionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionModel())
    val uiState: StateFlow<QuestionModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getQuestion()
        }
    }

    private fun getQuestion() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .build()

        val service = retrofit.create(QuestionApi::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("areas", JSONArray(ArrayList<String>()))
        jsonObject.put("years", JSONArray(ArrayList<String>()))

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getQuestion(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val question =
                        gson.fromJson(response.body()?.string(), QuestionModel::class.java)
                    println(question?.ask)
                    _uiState.update { currentState ->
                        currentState.copy(
                            answers = question.answers,
                            ask = question.ask,
                            rightAnswer = question.rightAnswer,
                            id = question.id,
                            url = question.url,
                            name = question.name,
                            description = question.description
                        )
                    }
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }
}