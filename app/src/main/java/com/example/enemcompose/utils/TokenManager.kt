package com.example.enemcompose.utils

import android.content.Context

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences(Constants.TOKEN_PATH, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(Constants.USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(Constants.USER_TOKEN, null)
    }

    fun deleteToken(){
        prefs.edit().remove(Constants.USER_TOKEN).apply()
    }

    fun saveYears(years: String) {
        val editor = prefs.edit()
        editor.putString(Constants.YEARS, years)
        editor.apply()
    }

    fun getYears(): String? {
        return prefs.getString(Constants.YEARS, "[]")
    }

    fun saveAreas(areas: String) {
        val editor = prefs.edit()
        editor.putString(Constants.AREAS, areas)
        editor.apply()
    }

    fun getAreas(): String? {
        return prefs.getString(Constants.AREAS, "[]")
    }
}