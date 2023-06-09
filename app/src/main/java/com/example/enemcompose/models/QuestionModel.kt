package com.example.enemcompose.models

data class QuestionModel (
    val id: String = "",
    val url: String = "",
    val name: String = "",
    val description: String = "",
    val ask: String = "",
    val answers: Answers = Answers(a = "", b="", c="", d="", e=""),
    val rightanswer: String = ""
)

data class Answers (
    val a: String,
    val b: String,
    val c: String,
    val d: String,
    val e: String
)
