package com.example.enemcompose.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.enemcompose.view.model.QuestionViewModel

class QuestionViewModelFactory(private val context: Context, private val random: Boolean) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = QuestionViewModel(context, random) as T
}