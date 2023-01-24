package com.example.enemcompose.view.model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.enemcompose.question.AuthApi
import com.example.enemcompose.utils.Constants
import com.example.enemcompose.utils.TokenManager
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
import org.json.JSONObject
import retrofit2.Retrofit

data class LoginModel(
    val token: String = "",
    val error: String = "",
    val isLoading: Boolean = false
)

data class TokenModel(
    val token: String
)

class LoginViewModel(context: Context) : ViewModel() {
    private val tokenManager: TokenManager = TokenManager(context)

    private val _uiState = MutableStateFlow(LoginModel())
    val uiState: StateFlow<LoginModel> = _uiState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .build()
    private val service = retrofit.create(AuthApi::class.java)

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    error = "Preencha todos os campos."
                )
            }
            return
        }

        val jsonObject = JSONObject()
            .put("email", email)
            .put("password", password)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        _loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.login(requestBody)
            withContext(Dispatchers.IO) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val token =
                        gson.fromJson(response.body()?.string(), TokenModel::class.java)

                    tokenManager.saveToken(token.token)

                } else if (response.code() < 500) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            error = "Email ou senha incorretos."
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(
                            error = "Ocorreu um erro interno."
                        )
                    }
                }
            }
        }
        _loading.value = true
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || confirmPassword.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    error = "Preencha todos os campos."
                )
            }
            return
        }

        if (password != confirmPassword) {
            _uiState.update { currentState ->
                currentState.copy(
                    error = "Senhas devem ser iguais."
                )
            }
            return
        }

        val jsonObject = JSONObject()
            .put("email", email)
            .put("name", name)
            .put("password", password)
            .put("confirmPassword", confirmPassword)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        _loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.createUser(requestBody)
            withContext(Dispatchers.Main) {
                if (response.code() < 500) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            error = "Dados inválidos."
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(
                            error = "Ocorreu um erro interno."
                        )
                    }
                }
            }
        }
        _loading.value = true
    }
}