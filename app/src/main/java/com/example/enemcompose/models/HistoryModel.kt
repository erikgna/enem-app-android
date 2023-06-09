package com.example.enemcompose.models

data class PercentageModel(
    val linguagens: Int = 0,
    val humanas: Int = 0,
    val natureza: Int = 0,
    val matematica: Int = 0,
)
data class HistoryQuestion(
    val url: String,
    val name: String,
    val rightanswer: String
)

data class QuestionHistoryModel(
    val question: HistoryQuestion? = null,
    val correct: Boolean? = false
)