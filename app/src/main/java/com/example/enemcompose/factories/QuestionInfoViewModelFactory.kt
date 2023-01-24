package com.example.enemcompose.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.enemcompose.view.model.QuestionInfoViewModel

class QuestionInfoViewModelFactory(private val id: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        QuestionInfoViewModel(id) as T
}
