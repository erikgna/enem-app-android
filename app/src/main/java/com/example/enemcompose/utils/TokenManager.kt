package com.example.enemcompose.utils

import android.content.Context
import com.auth0.android.jwt.JWT

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences(Constants.TOKEN_PATH, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(Constants.USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        val token = prefs.getString(Constants.USER_TOKEN, null) ?: return null

        val jwt = JWT(token);
        if (jwt.isExpired(10)) {
            return null
        }
        return token
    }

    fun deleteToken() {
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