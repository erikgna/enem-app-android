package com.example.enemcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.enemcompose.*
import com.example.enemcompose.components.CustomInput
import com.example.enemcompose.components.PrimaryButton
import com.example.enemcompose.components.SecondaryButton
import com.example.enemcompose.factories.LoginViewModelFactory
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.red
import com.example.enemcompose.ui.theme.white
import com.example.enemcompose.view.model.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController
) {
    val loginViewModel: LoginViewModel =
        viewModel(factory = LoginViewModelFactory(LocalContext.current))

    val uiState by loginViewModel.uiState.collectAsState()

    val emailState = remember { mutableStateOf("") }
    val nameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val confirmPasswordState = remember { mutableStateOf("") }

    fun navigateToLogin() {
        navController.navigate(Screen.LoginScreen.route) {
            popUpTo(Screen.RegisterScreen.route) {
                inclusive = true
            }
        }
    }

    fun callRegister() {
        loginViewModel.register(
            name = nameState.value,
            email = emailState.value,
            password = passwordState.value,
            confirmPassword = confirmPasswordState.value
        )

        emailState.value = ""
        nameState.value = ""
        passwordState.value = ""
        confirmPasswordState.value = ""

        navController.navigate(Screen.LoginScreen.route) {
            popUpTo(Screen.LoginScreen.route) {
                inclusive = true
            }
        }
    }

    Scaffold(
        content = { paddingValue ->
            Box(
                modifier = Modifier
                    .background(darkBlue)
                    .padding(paddingValue)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(darkBlue)
                        .padding(16.dp)
                ) {
                    Logo()
                    Spacer(modifier = Modifier.height(64.dp))
                    Text(
                        text = "Registro",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = white
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Crie uma conta para ter acesso ao seu histórico de questões",
                        fontSize = 16.sp,
                        color = white
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    CustomInput(
                        iconDescription = "Name Icon",
                        icon = Icons.Rounded.Person,
                        placeholder = "Nome Completo",
                        changeString = nameState,
                        hasError = uiState.error.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomInput(
                        iconDescription = "Email icon",
                        icon = Icons.Rounded.Email,
                        placeholder = "Email",
                        changeString = emailState,
                        keyboard = KeyboardType.Email,
                        hasError = uiState.error.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomInput(
                        iconDescription = "Password icon",
                        icon = Icons.Rounded.Lock,
                        placeholder = "Senha",
                        changeString = passwordState,
                        keyboard = KeyboardType.Password,
                        password = true,
                        hasError = uiState.error.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomInput(
                        iconDescription = "Confirm password icon",
                        icon = Icons.Rounded.Lock,
                        placeholder = "Confirmar Senha",
                        changeString = confirmPasswordState,
                        keyboard = KeyboardType.Password,
                        password = true,
                        hasError = uiState.error.isNotEmpty()
                    )
                    if (uiState.error.isNotEmpty()) {
                        Text(
                            text = uiState.error,
                            color = red,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton(text = "Registrar", click = { callRegister()                    })
                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonDivider()
                    Spacer(modifier = Modifier.height(16.dp))
                    SecondaryButton(text = "Entrar na conta", click = { navigateToLogin() })
                }
            }
        })
}