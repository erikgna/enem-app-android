package com.example.enemcompose.components

import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enemcompose.ui.theme.white

@Composable
fun SecondaryButton(
    text: String,
    click: () -> Unit
) {
    ElevatedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = click,
        border = BorderStroke(1.dp, white),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = white),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text, color = white,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}