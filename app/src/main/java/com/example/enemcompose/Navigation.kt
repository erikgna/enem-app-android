package com.example.enemcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.enemcompose.screens.*
import com.example.enemcompose.utils.TokenManager

@Composable
fun Navigation(showAdd: () -> Unit) {
    val tokenManager = TokenManager(LocalContext.current)
    val navController = rememberNavController()
    val adCount = rememberSaveable { mutableStateOf(0) }

    fun countAndShowAd(){
        if(adCount.value == 11){
            adCount.value = 0
            showAdd()
        }
        adCount.value++
    }

    NavHost(
        navController = navController,
        startDestination = if (tokenManager.getToken() == null) {
            Screen.MainScreen.route
        } else {
            Screen.HomeScreen.route
        }
    ) {
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
        composable(route = "${Screen.QuestionScreen.route}/{random}") {
            val random: String? = it.arguments?.getString("random")

            QuestionScreen(
                navController = navController, random?.contains("true") ?: true, countAndShowAd = { countAndShowAd() }
            )
        }
        composable(route = Screen.HistoryScreen.route) {
            HistoryScreen(navController = navController)
        }
        composable(route = "${Screen.QuestionInfoScreen.route}/{id}") {
            val id: String = it.arguments?.getString("id") ?: ""
            QuestionInfoScreen(navController = navController, id = id)
        }
    }
}




