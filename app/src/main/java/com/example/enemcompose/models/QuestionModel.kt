package com.example.enemcompose.models

data class Answer(val a: String, val b: String, val c: String, val d: String, val e: String)

data class QuestionModel (
    val id: String? = null,
    val url: String? = null,
    val name: String? = null,
    val description: String? = null,
    val ask: String? = null,
    val answers: Answer? = null,
    val rightAnswer: String? = null
)