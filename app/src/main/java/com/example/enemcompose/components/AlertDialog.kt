package com.example.enemcompose.components

import androidx.compose.material.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.enemcompose.ui.theme.darkBlue

@Composable
fun MyAlertDialog(text: String, action: () -> Unit) {
    AlertDialog(
        backgroundColor = darkBlue,
        onDismissRequest = action,
        text = {
            Text(text)
        },
        confirmButton = {
            PrimaryButton(
                click = action,
                text = "Fechar"
            )
        },
    )
}