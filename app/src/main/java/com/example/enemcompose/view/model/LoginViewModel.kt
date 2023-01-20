package com.example.enemcompose.view.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.enemcompose.Constants
import com.example.enemcompose.models.QuestionModel
import com.example.enemcompose.question.AuthApi
import com.example.enemcompose.question.QuestionApi
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit

data class LoginModel (
    val token: String = ""
)

class LoginViewModel  : ViewModel() {
    private val _uiState = MutableStateFlow(LoginModel())
    val uiState: StateFlow<LoginModel> = _uiState.asStateFlow()

    fun login() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .build()

        val service = retrofit.create(AuthApi::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("email", "thehautd@gmail.com")
        jsonObject.put("password", "123456")

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.login(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
//                    val gson = Gson()
//                    val question =
//                        gson.fromJson(response.body()?.string(), QuestionModel::class.java)
                    println(response.body()?.string())

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }
}