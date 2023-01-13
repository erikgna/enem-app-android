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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.enemcompose.Screen
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.primaryBlue
import com.example.enemcompose.ui.theme.white

@Composable
fun MyBottomNavigation(navController: NavController) {
    val iconSize = 24.dp
    val labelSize = 14.sp

    NavigationBar(
        containerColor = darkBlue,
    ) {
        NavigationBarItem(
            selected = false, onClick = { navController.navigate(Screen.HomeScreen.route) },
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
            selected = false, onClick = { navController.navigate(Screen.HistoryScreen.route) },
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
            selected = false, onClick = { navController.navigate(Screen.LoginScreen.route) },
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