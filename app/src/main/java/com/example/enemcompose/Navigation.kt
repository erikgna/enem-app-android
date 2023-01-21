package com.example.enemcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.enemcompose.screens.*

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = "${Screen.QuestionScreen.route}/{areas}/{years}") {
            val areas = it.arguments?.getString("areas")
            val years = it.arguments?.getString("years")
            println(areas)
            println(years)
            QuestionScreen(navController = navController)
        }
        composable(route = Screen.HistoryScreen.route) {
            HistoryScreen(navController = navController)
        }
    }
}



