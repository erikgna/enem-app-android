package com.example.enemcompose.question

data class Answer(val msg: String, val index: String)

data class QuestionModel (
    val id: String,
    val url: String,
    val name: String,
    val description: String,
    val ask: String,
    val answers: ArrayList<Answer>,
    val rightAnswer: String

)