package com.example.enemcompose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.enemcompose.ui.theme.white

@Composable
fun CustomInput(
    iconDescription: String,
    icon: ImageVector,
    hint: String,
    changeString: MutableState<String>,
    keyboard: KeyboardType = KeyboardType.Text
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val borderSize = 1.dp.toPx()

                drawLine(
                    white,
                    Offset(0f, size.height),
                    Offset(size.width, size.height),
                    borderSize
                )
            }
            .padding(vertical = 6.dp),
    ) {
        Icon(
            icon,
            contentDescription = iconDescription,
            tint = white,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            keyboardOptions = KeyboardOptions(keyboardType = keyboard),
            modifier = Modifier.fillMaxWidth(),
            value = hint,
            textStyle = TextStyle(color = white),
            onValueChange = { newText -> changeString.value = newText })
    }
}