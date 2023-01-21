package com.example.enemcompose.view.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enemcompose.models.Answer
import com.example.enemcompose.models.QuestionModel
import com.example.enemcompose.question.QuestionApi
import com.example.enemcompose.utils.Constants
import com.example.enemcompose.utils.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import com.example.enemcompose.utils.Interceptor

class QuestionViewModel(private val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionModel())
    val uiState: StateFlow<QuestionModel> = _uiState.asStateFlow()

    private val _feedback = MutableStateFlow(String())
    val feedback: StateFlow<String> = _feedback.asStateFlow()

//    private val _feedback = MutableStateFlow(String())
//    val feedback: StateFlow<String> = _feedback.asStateFlow()

    private val service = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL).client(OkHttpClient.Builder().apply {
            addInterceptor(Interceptor(context))
        }.build()).build().create(QuestionApi::class.java)

    init {
        viewModelScope.launch {
            getQuestion()
            getUserQuestions()
        }
    }

    fun getOneQuestion() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getQuestionById("")

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
//                    val gson = Gson()
//                    val question =
//                        gson.fromJson(response.body()?.string(), QuestionModel::class.java)

                    println(response.body()?.string())
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

    fun getUserQuestions() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getUserQuestions()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val question =
                        gson.fromJson(response.body()?.string(), QuestionModel::class.java)

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

    fun getQuestion() {
        _uiState.update { currentState ->
            currentState.copy(
                answers = Answer("a", "b", "c", "d", "e"),
                ask = "question.ask",
                rightAnswer = "a",
                id = "question.id",
                url = "question.url",
                name = "question.name",
                description = "question.description"
            )
        }

//        val jsonObject = JSONObject()
//        jsonObject.put("areas", JSONArray(ArrayList<String>()))
//        jsonObject.put("years", JSONArray(ArrayList<String>()))
//
//        val jsonObjectString = jsonObject.toString()
//        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = service.getQuestion(requestBody)
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    val gson = Gson()
//                    val question =
//                        gson.fromJson(response.body()?.string(), QuestionModel::class.java)
//
//                    _uiState.update { currentState ->
//                        currentState.copy(
//                            answers = question.answers,
//                            ask = question.ask,
//                            rightAnswer = question.rightAnswer,
//                            id = question.id,
//                            url = question.url,
//                            name = question.name,
//                            description = question.description
//                        )
//                    }
//                } else {
//                    Log.e("RETROFIT_ERROR", response.code().toString())
//                }
//            }
//        }
    }

    fun addQuestion(correct: Boolean) {
        val jsonObject = JSONObject()
        jsonObject.put("id", _uiState.value.id)
        jsonObject.put("correct", correct)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.addNewQuestion(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    println("deu certo!")
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

    fun eraseHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.eraseHistory()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _feedback.value = "Histórico deletado com sucesso."
                } else {
                    _feedback.value = "Ocorreu um erro ao apagar o histórico."
                }
            }
        }
    }

    fun resetFeedback() {
        _feedback.value = ""
    }
}