package com.example.enemcompose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.enemcompose.ui.theme.*

data class Answer(val msg: String, val index: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun QuestionScreen() {
    val title = "Questao 23 - Ciencias Humanas e suas Tecnologias - 2011"
    val desc = "teste <br /> teste".replace("<br />", "\n")
    val ask = "Simplesmente a descricao da pergunta e do problema em si ou apenas a respota?"
    val asks = listOf(
        Answer(msg = "Teste", index = "a"),
        Answer(msg = "Teste", index = "a")
    )

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
                    Logo()
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = title,
                        color = white
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = desc, color = white)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = ask,
                        color = white
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    asks.forEach { item ->
                        AnswerItem(msg = "Teste teste", isCorrect = false, isAnswer = true)
                    }
                }
            }
        })
}

@Composable
fun AnswerItem(msg: String, isCorrect: Boolean, isAnswer: Boolean) {
    Box(
        modifier = if (isAnswer) {
            Modifier
                .padding(bottom = 8.dp)
                .border(
                    border = BorderStroke(2.dp, primaryBlue),
                    shape = RoundedCornerShape(50.dp),
                )
                .fillMaxWidth()
        } else if (isCorrect) {
            Modifier
                .padding(bottom = 8.dp)
                .border(
                    border = BorderStroke(2.dp, red),
                    shape = RoundedCornerShape(50.dp),
                )
                .fillMaxWidth()
        } else {
            Modifier
                .padding(bottom = 8.dp)
                .border(
                    border = BorderStroke(2.dp, green),
                    shape = RoundedCornerShape(50.dp),
                )
                .fillMaxWidth()
        }
    ) {
        Text(
            text = msg,
            color = white,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
    }
}