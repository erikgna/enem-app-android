package com.example.enemcompose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enemcompose.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HistoryScreen() {
    Scaffold(
        bottomBar = {
            Column() {
                Spacer(
                    modifier = Modifier
                        .background(white)
                        .fillMaxWidth()
                        .height(2.dp)
                )
                MyBottomNavigation()
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .background(darkBlue)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(darkBlue)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Pontuação",
                        color = white,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Score(
                        text = "Ciencias Humanas e suas Tecnologias",
                        percentage = .2f,
                        modifier = Modifier
                            .size(size = 80.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Score(
                        text = "Ciencias Humanas e suas Tecnologias",
                        percentage = 1f,
                        modifier = Modifier
                            .size(size = 80.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Score(
                        text = "Ciencias Humanas e suas Tecnologias",
                        percentage = 1f,
                        modifier = Modifier
                            .size(size = 80.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Score(
                        text = "Ciencias Humanas e suas Tecnologias",
                        percentage = .2f,
                        modifier = Modifier
                            .size(size = 80.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Questoes respondidas",
                        color = white,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    HistoryItem(
                        myColor = green,
                        text = "Questao 89 - Ciencias Humanas e suas Tecnologias - 2011 "
                    )
                    HistoryItem(
                        myColor = red,
                        text = "Questao 89 - Ciencias Humanas e suas Tecnologias - 2011 "
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PrimaryButton(text = "Deletar histórico", click = {})
                    Spacer(
                        modifier = Modifier
                            .height(96.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun HistoryItem(myColor: Color, text: String) {
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .border(
                border = BorderStroke(2.dp, myColor),
                shape = RoundedCornerShape(50.dp),
            )
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = white,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun Score(percentage: Float, modifier: Modifier, text: String) {
    Column(
        modifier= Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = white,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        CircularProgressIndicator(
            modifier = modifier,
            color = green,
            strokeWidth = 4.dp,
            progress = percentage,

            )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = (percentage * 100).toString(),
            color = white,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}