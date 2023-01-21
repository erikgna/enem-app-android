package com.example.enemcompose.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.enemcompose.Screen
import com.example.enemcompose.factories.QuestionViewModelFactory
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.primaryBlue
import com.example.enemcompose.ui.theme.white
import com.example.enemcompose.view.model.QuestionViewModel

@Composable
fun MyBottomNavigation(navController: NavController) {
    val iconSize = 24.dp
    val labelSize = 14.sp

    val index = remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = darkBlue,
    ) {
        NavigationBarItem(
            selected = index.value == 0,
            onClick = {
                navController.navigate(Screen.HomeScreen.route)
            },
            icon = {
                Icon(
                    Icons.Rounded.Home, contentDescription = "Home Icon",
                    tint = primaryBlue,
                    modifier = Modifier.size(iconSize)
                )
            },
            label = {
                Text(
                    text = "Inicio",
                    color = primaryBlue,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = labelSize
                )
            },
        )
        NavigationBarItem(
            selected = index.value == 1,
            onClick = {
                navController.navigate(Screen.HistoryScreen.route)
            },
            icon = {
                Icon(
                    Icons.Rounded.List, contentDescription = "History Icon",
                    tint = white,
                    modifier = Modifier.size(iconSize)
                )
            },
            label = {
                Text(
                    text = "Histórico",
                    color = white,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = labelSize
                )
            },
        )
        NavigationBarItem(
            selected = index.value == 2,
            onClick = {
                navController.navigate(Screen.LoginScreen.route)
                index.value = 2
            },
            icon = {
                Icon(
                    Icons.Rounded.AccountCircle, contentDescription = "Account Icon",
                    tint = white,
                    modifier = Modifier.size(iconSize)
                )
            },
            label = {
                Text(
                    text = "Conta",
                    color = white,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = labelSize
                )
            },
        )
    }
}