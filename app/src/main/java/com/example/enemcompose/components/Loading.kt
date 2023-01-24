package com.example.enemcompose.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.enemcompose.ui.theme.primaryBlue
import kotlinx.coroutines.delay

@Composable()
fun Loading(){
    val progress = remember { mutableStateOf(.1f) }

    LaunchedEffect(Unit) {
        while(true) {
            if(progress.value > .9f){
                progress.value = .1f
            }else{
                progress.value = progress.value + .1f
            }
            delay(100)
        }
    }

    CircularProgressIndicator(
        modifier = Modifier.size(64.dp),
        color = primaryBlue,
        strokeWidth = 6.dp,
        progress = progress.value
    )
}