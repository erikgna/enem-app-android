package com.example.enemcompose

import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.primaryBlue
import com.example.enemcompose.ui.theme.white

@Composable
fun MyBottomNavigation() {
    val iconSize = 24.dp
    val labelSize = 14.sp

    NavigationBar(
        containerColor = darkBlue,
    ) {
        NavigationBarItem(
            selected = false, onClick = {},
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
            selected = false, onClick = {},
            icon = {
                Icon(
                    Icons.Rounded.List, contentDescription = "Home Icon",
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
            selected = false, onClick = {},
            icon = {
                Icon(
                    Icons.Rounded.AccountCircle, contentDescription = "Home Icon",
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