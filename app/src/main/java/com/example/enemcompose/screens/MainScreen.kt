package com.example.enemcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.enemcompose.Logo
import com.example.enemcompose.components.PrimaryButton
import com.example.enemcompose.Screen
import com.example.enemcompose.components.SecondaryButton
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.white
import com.example.enemcompose.view.model.teste

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    fun navigateToRegister() {
        navController.navigate(Screen.RegisterScreen.route)
    }

    fun navigateToLogin() {
        teste()
        //navController.navigate(Screen.LoginScreen.route)
    }

    fun navigateToHome() {
        navController.navigate(Screen.HomeScreen.route)
    }

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .background(darkBlue)
                    .padding(paddingValues)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(darkBlue)
                        .padding(16.dp)
                ) {
                    Logo()
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        modifier = Modifier.width(300.dp),
                        text = "Treine para o ENEM com centenas das questões passadas nos últimos anos, e veja seu histórico de acertos e erros.",
                        fontSize = 16.sp,
                        color = white
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton(text = "Entrar na conta", click = { navigateToLogin() })
                    Spacer(modifier = Modifier.height(16.dp))
                    SecondaryButton(text = "Criar nova conta", click = { navigateToRegister() })
                    Spacer(modifier = Modifier.height(24.dp))
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