package com.example.enemcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.red
import com.example.enemcompose.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomInput(
    iconDescription: String,
    icon: ImageVector,
    placeholder: String,
    changeString: MutableState<String>,
    keyboard: KeyboardType = KeyboardType.Text,
    password: Boolean = false,
    hasError: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    val isSecret = remember { mutableStateOf(password) }

    TextField(
        isError = hasError,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.White,
                fontSize = 16.sp,
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboard),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = darkBlue),
        value = changeString.value,
        textStyle = TextStyle(color = white),
        onValueChange = { newText -> changeString.value = newText },
        singleLine = true,
        visualTransformation = if (isSecret.value) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        trailingIcon = {
            if (password) {
                IconButton(
                    onClick = {
                        isSecret.value = !isSecret.value
                    },
                ) {
                    Icon(
                        if (isSecret.value) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = "Show/Unshow password",
                        tint = white
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                icon,
                contentDescription = iconDescription,
                tint = white
            )

        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            textColor = Color.White,
            cursorColor = Color.White,
            errorBorderColor = red
        )
    )
}