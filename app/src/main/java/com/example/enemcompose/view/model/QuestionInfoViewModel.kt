package com.example.enemcompose.view.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enemcompose.models.QuestionModel
import com.example.enemcompose.question.QuestionApi
import com.example.enemcompose.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class QuestionInfoViewModel(id: String) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionModel())
    val uiState: StateFlow<QuestionModel> = _uiState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _feedback = MutableStateFlow(String())
    val feedback: StateFlow<String> = _feedback.asStateFlow()

    private val service = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .build().create(QuestionApi::class.java)

    init {
        viewModelScope.launch {
            getOneQuestion(id)
        }
    }

    private fun getOneQuestion(id: String) {
        _loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getQuestionById(id)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val gson = Gson()
                        val question =
                            gson.fromJson(response.body()?.string(), QuestionModel::class.java)

                        _uiState.update { currentState ->
                            currentState.copy(
                                answers = question.answers,
                                ask = question.ask,
                                rightanswer = question.rightanswer,
                                id = question.id,
                                url = question.url,
                                name = question.name,
                                description = question.description
                            )
                        }
                        _loading.value = false
                    } else {
                        _feedback.value = "Ocorreu um erro, por favor, tente novamente."
                        _loading.value = false
                    }
                }
            } catch (_: java.lang.Exception) {
                _feedback.value = "Ocorreu um erro desconhecido, por favor, tente novamente."
                _loading.value = false
            }
        }
    }
}