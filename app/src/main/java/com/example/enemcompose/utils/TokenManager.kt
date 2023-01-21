package com.example.enemcompose.utils

import android.content.Context

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences(Constants.TOKEN_PATH, Context.MODE_PRIVATE)

    fun saveToken(token: String){
        val editor = prefs.edit()
        editor.putString(Constants.USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(Constants.USER_TOKEN, null)
    }
}