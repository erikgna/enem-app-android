package com.example.enemcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.enemcompose.*
import com.example.enemcompose.components.CustomInput
import com.example.enemcompose.components.Loading
import com.example.enemcompose.components.PrimaryButton
import com.example.enemcompose.components.SecondaryButton
import com.example.enemcompose.factories.LoginViewModelFactory
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.red
import com.example.enemcompose.ui.theme.white
import com.example.enemcompose.view.model.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController
) {
    val loginViewModel: LoginViewModel =
        viewModel(factory = LoginViewModelFactory(LocalContext.current))
    val uiState by loginViewModel.uiState.collectAsState()
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val loading by loginViewModel.loading.collectAsState()

    fun callLogin() {
        loginViewModel.login(
            email = emailState.value,
            password = passwordState.value,
            navController = navController
        )

        emailState.value = ""
        passwordState.value = ""
    }

    fun navigateToRegister() {
        navController.navigate(Screen.RegisterScreen.route) {
            popUpTo(Screen.LoginScreen.route) {
                inclusive = true
            }
        }
    }

    fun navigateToHome() {
        navController.navigate(Screen.HomeScreen.route) {
            popUpTo(Screen.MainScreen.route) {
                inclusive = true
            }
        }
    }

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .background(darkBlue)
                    .padding(paddingValues)
            ) {
                if (loading) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(darkBlue)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Loading()
                    }
                } else Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(darkBlue)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Logo()
                    Spacer(modifier = Modifier.height(64.dp))
                    Text(
                        text = "Login",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = white
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Entre na sua conta para continuar",
                        fontSize = 16.sp,
                        color = white
                    )
                    Spacer(modifier = Modifier.height(32.dp))
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
                        placeholder = "********",
                        changeString = passwordState,
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
                    PrimaryButton(text = "Entrar", click = { callLogin() })
                    Spacer(modifier = Modifier.height(16.dp))
                    SecondaryButton(text = "Criar nova conta", click = { navigateToRegister() })
                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonDivider()
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(
                        onClick = { navigateToHome() },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Entrar de forma anônima",
                            textDecoration = TextDecoration.Underline,
                            color = white,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        })
}

@Composable
fun ButtonDivider() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .background(color = white)
                .fillMaxWidth(.47f)
                .height(2.dp)
        )
        Text(
            text = "ou",
            color = white,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Box(
            modifier = Modifier
                .background(color = white)
                .fillMaxWidth(1f)
                .height(2.dp)
        )
    }
}