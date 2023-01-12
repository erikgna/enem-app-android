package com.example.enemcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
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

    Scaffold(
        content = {paddingValue ->
            Box(modifier = Modifier.background(darkBlue).padding(paddingValue)) {
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
                        hint = "Nome Completo",
                        changeString = nameState
                    )
                    Spacer(modifier = Modifier.height(16.dp))
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
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomInput(
                        iconDescription = "Confirm password icon",
                        icon = Icons.Rounded.Lock,
                        hint = "Confirmar Senha",
                        changeString = confirmPasswordState,
                        keyboard = KeyboardType.Password
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton(text = "Registrar", click = {})
                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonDivider()
                    Spacer(modifier = Modifier.height(16.dp))
                    SecondaryButton(text = "Entrar na conta", click = { navigateToLogin() })
                }
            }
        })
}