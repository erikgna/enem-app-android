package com.example.enemcompose.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.Logout
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
import androidx.navigation.NavController
import com.example.enemcompose.Screen
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.primaryBlue
import com.example.enemcompose.ui.theme.white
import com.example.enemcompose.utils.TokenManager

@Composable
fun MyBottomNavigation(navController: NavController) {
    val tokenManager = TokenManager(LocalContext.current)

    val iconSize = 24.dp
    val labelSize = 14.sp

    val index = remember {
        mutableStateOf(
            if (navController.currentDestination.toString().contains("home")) {
                1
            } else {
                2
            }
        )
    }

    NavigationBar(
        containerColor = darkBlue,
    ) {
        NavigationBarItem(
            selected = index.value == 1,
            onClick = {
                index.value = 1
                navController.navigate(Screen.HomeScreen.route)
            },
            icon = {
                Icon(
                    Icons.Rounded.Home, contentDescription = "Home Icon",
                    tint = if (index.value == 1) primaryBlue else {
                        white
                    },
                    modifier = Modifier.size(iconSize)
                )
            },
            label = {
                Text(
                    text = "Inicio",
                    color = if (index.value == 1) primaryBlue else {
                        white
                    },
                    fontWeight = FontWeight.SemiBold,
                    fontSize = labelSize
                )
            },
        )
        if (tokenManager.getToken() != null) {
            NavigationBarItem(
                selected = index.value == 2,
                onClick = {
                    index.value = 2
                    navController.navigate(Screen.HistoryScreen.route)
                },
                icon = {
                    Icon(
                        Icons.Rounded.List, contentDescription = "History Icon",
                        tint = if (index.value == 2) primaryBlue else {
                            white
                        },
                        modifier = Modifier.size(iconSize)
                    )
                },
                label = {
                    Text(
                        text = "Hist??rico",
                        color = if (index.value == 2) primaryBlue else {
                            white
                        },
                        fontWeight = FontWeight.SemiBold,
                        fontSize = labelSize
                    )
                },
            )
        }
        if (tokenManager.getToken() == null) {
            NavigationBarItem(
                selected = index.value == 3,
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                    index.value = 3
                },
                icon = {
                    Icon(
                        Icons.Rounded.Login, contentDescription = "Account Icon",
                        tint = white,
                        modifier = Modifier.size(iconSize)
                    )
                },
                label = {
                    Text(
                        text = "Entrar",
                        color = white,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = labelSize
                    )
                },
            )
        } else {
            NavigationBarItem(
                selected = index.value == 3,
                onClick = {
                    tokenManager.deleteToken()
                    navController.navigate(Screen.LoginScreen.route)
                },
                icon = {
                    Icon(
                        Icons.Rounded.Logout, contentDescription = "Account Icon",
                        tint = white,
                        modifier = Modifier.size(iconSize)
                    )
                },
                label = {
                    Text(
                        text = "Sair",
                        color = white,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = labelSize
                    )
                },
            )
        }

    }
}