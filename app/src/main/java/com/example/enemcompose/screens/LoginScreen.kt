package com.example.enemcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.enemcompose.*
import com.example.enemcompose.components.CustomInput
import com.example.enemcompose.components.PrimaryButton
import com.example.enemcompose.components.SecondaryButton
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.white
import com.example.enemcompose.view.model.LoginViewModel
import com.example.enemcompose.view.model.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    fun navigateToRegister() {
        navController.navigate(Screen.RegisterScreen.route) {
            popUpTo(Screen.LoginScreen.route) {
                inclusive = true
            }
        }
    }

    Scaffold(
        content = {paddingValues ->
            Box(modifier = Modifier.background(darkBlue).padding(paddingValues)) {
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
                        hint = "Email",
                        changeString = emailState,
                        keyboard = KeyboardType.Email
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomInput(
                        iconDescription = "Password icon",
                        icon = Icons.Rounded.Lock,
                        hint = "Senha",
                        changeString = passwordState,
                        keyboard = KeyboardType.Password
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton(text = "Entrar", click = {viewModel.login()})
                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonDivider()
                    Spacer(modifier = Modifier.height(16.dp))
                    SecondaryButton(text = "Criar nova conta", click = { navigateToRegister() })
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