package com.example.enemcompose.view.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enemcompose.models.PercentageModel
import com.example.enemcompose.models.QuestionHistoryModel
import com.example.enemcompose.question.QuestionApi
import com.example.enemcompose.utils.Constants
import com.example.enemcompose.utils.Interceptor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class HistoryViewModel(private val context: Context) : ViewModel() {
    private val _history = MutableStateFlow(listOf(QuestionHistoryModel()))
    val history: StateFlow<List<QuestionHistoryModel>> = _history.asStateFlow()

    private val _percentages = MutableStateFlow(PercentageModel())
    val percentages: StateFlow<PercentageModel> = _percentages.asStateFlow()

    private val _feedback = MutableStateFlow(String())
    val feedback: StateFlow<String> = _feedback.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val service = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL).client(OkHttpClient.Builder().apply {
            addInterceptor(Interceptor(context))
        }.build()).build().create(QuestionApi::class.java)

    init {
        viewModelScope.launch {
            getUserQuestions()
        }
    }

    private fun getUserQuestions() {
        _loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getUserQuestions()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseString = response.body()?.string()
                        val gson = Gson()
                        val listType = object : TypeToken<List<QuestionHistoryModel>>() {}.type
                        val questions =
                            gson.fromJson<List<QuestionHistoryModel>>(responseString, listType)

                        _history.value = questions
                        calculatePercentages(questions)
                        _loading.value = false
                    } else {
                        _history.value = listOf()
                        _feedback.value = "Não foi possível recuperar o seu histórico."
                        _loading.value = false
                    }
                }
            } catch (_: java.lang.Exception) {
                _feedback.value = "Ocorreu um erro, por favor, tente novamente."
                _loading.value = false
            }
        }
    }

    private fun calculatePercentages(questions: List<QuestionHistoryModel>) {
        var humanasCorrect = 0
        var humanasWrong = 0
        var naturezaCorrect = 0
        var naturezaWrong = 0
        var linguagensCorrect = 0
        var linguagensWrong = 0
        var matematicaCorrect = 0
        var matematicaWrong = 0

        questions.forEach {
            if (it.question?.url?.contains("humanas") == true) {
                if (it.correct == true) {
                    humanasCorrect += 1
                } else {
                    humanasWrong += 1
                }
            } else if (it.question?.url?.contains("linguagens") == true) {
                if (it.correct == true) {
                    linguagensCorrect += 1
                } else {
                    linguagensWrong += 1
                }
            } else if (it.question?.url?.contains("matematica") == true) {
                if (it.correct == true) {
                    matematicaCorrect += 1
                } else {
                    matematicaWrong += 1
                }
            } else if (it.question?.url?.contains("naturais") == true) {
                if (it.correct == true) {
                    naturezaCorrect += 1
                } else {
                    naturezaWrong += 1
                }
            }
        }

        val hundredPercentage = 100

        _percentages.update { currentState ->
            currentState.copy(
                humanas = (humanasCorrect * hundredPercentage) / if ((humanasCorrect + humanasWrong) == 0) 100 else {
                    (humanasCorrect + humanasWrong)
                },
                natureza = (naturezaCorrect * hundredPercentage) / if ((naturezaCorrect + naturezaWrong) == 0) 100 else {
                    (naturezaCorrect + naturezaWrong)
                },
                linguagens = (linguagensCorrect * hundredPercentage) / if ((linguagensCorrect + linguagensWrong) == 0) 100 else {
                    (linguagensCorrect + linguagensWrong)
                },
                matematica = (matematicaCorrect * hundredPercentage) / if ((matematicaCorrect + matematicaWrong) == 0) 100 else {
                    (matematicaCorrect + matematicaWrong)
                },
            )
        }
    }

    fun eraseHistory() {
        _loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.eraseHistory()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _history.value = listOf()
                        _feedback.value = "Histórico deletado com sucesso."
                        _loading.value = false
                    } else {
                         _feedback.value = "Ocorreu um erro ao apagar o histórico."
                        _loading.value = false
                    }
                }
            } catch (_: java.lang.Exception) {
                _feedback.value = "Ocorreu um erro, por favor, tente novamente."
                _loading.value = false
            }

        }
    }

    fun resetFeedback() {
        _feedback.value = ""
    }
}