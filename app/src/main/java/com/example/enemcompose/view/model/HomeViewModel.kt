package com.example.enemcompose.view.model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.enemcompose.utils.TokenManager
import com.google.gson.Gson

class HomeViewModel(context: Context) : ViewModel() {
    private val tokenManager: TokenManager = TokenManager(context)

    fun saveYears(years: List<String>) {
        tokenManager.saveYears(Gson().toJson(years))
    }

    fun saveAreas(areas: List<String>) {
        tokenManager.saveAreas(Gson().toJson(areas))
    }
}