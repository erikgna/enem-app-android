package com.example.enemcompose.view.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enemcompose.models.QuestionModel
import com.example.enemcompose.question.QuestionApi
import com.example.enemcompose.utils.Constants
import com.example.enemcompose.utils.Interceptor
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

class QuestionViewModel(private val context: Context, private val random: Boolean) : ViewModel() {
    private val tokenManager: TokenManager = TokenManager(context)

    private val _uiState = MutableStateFlow(QuestionModel())
    val uiState: StateFlow<QuestionModel> = _uiState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow(String())
    val error: StateFlow<String> = _error.asStateFlow()

    private val service = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL).client(OkHttpClient.Builder().apply {
            addInterceptor(Interceptor(context))
        }.build()).build().create(QuestionApi::class.java)

    init {
        viewModelScope.launch {
            getQuestion()
        }
    }

    fun getQuestion() {
        val areas: String = tokenManager.getAreas()!!
        val years: String = tokenManager.getYears()!!

        val areasList = areas.replace("\"", "").replace("[", "").replace("]", "").split(",")
        val yearsList = years.replace("\"", "").replace("[", "").replace("]", "").split(",")

        val jsonObject = JSONObject()
        if (random) {
            jsonObject.put("areas", JSONArray(ArrayList<String>()))
            jsonObject.put("years", JSONArray(ArrayList<String>()))
        } else {
            jsonObject.put(
                "areas", JSONArray(areasList)
            )
            jsonObject.put(
                "years", JSONArray(yearsList)
            )
        }

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        _loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getQuestion(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val stringResponse = response.body()?.string()
                    val gson = Gson()
                    val question =
                        gson.fromJson(stringResponse, QuestionModel::class.java)

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
                    _error.value = "Ocorreu um erro, por favor, tente novamente."
                }
            }
            _loading.value = false
        }
    }

    fun addQuestion(correct: Boolean) {
        val jsonObject = JSONObject()
        jsonObject.put("id", _uiState.value.id)
        jsonObject.put("correct", correct)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        _loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.addNewQuestion(requestBody)
            withContext(Dispatchers.Main) {
                if (!response.isSuccessful) {
                    _error.value = "Ocorreu um erro, por favor, tente novamente."
                }
            }
        }
        _loading.value = false
    }
}