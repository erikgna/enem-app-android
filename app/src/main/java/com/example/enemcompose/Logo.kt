package com.example.enemcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enemcompose.ui.theme.white

@Composable
fun Logo(){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Unsolved",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = white
        )
    }
}