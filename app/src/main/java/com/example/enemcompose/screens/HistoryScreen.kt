package com.example.enemcompose.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.enemcompose.Screen
import com.example.enemcompose.components.Loading
import com.example.enemcompose.components.MyAlertDialog
import com.example.enemcompose.components.MyBottomNavigation
import com.example.enemcompose.components.PrimaryButton
import com.example.enemcompose.factories.HistoryViewModelFactory
import com.example.enemcompose.ui.theme.*
import com.example.enemcompose.view.model.HistoryViewModel
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {
    val historyViewModel: HistoryViewModel =
        viewModel(factory = HistoryViewModelFactory(LocalContext.current))

    val feedback by historyViewModel.feedback.collectAsState()
    val loading by historyViewModel.loading.collectAsState()
    val history by historyViewModel.history.collectAsState()
    val percentages by historyViewModel.percentages.collectAsState()

    Scaffold(
        bottomBar = {
            Column {
                Spacer(
                    modifier = Modifier
                        .background(white)
                        .fillMaxWidth()
                        .height(2.dp)
                )
                MyBottomNavigation(navController = navController)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(darkBlue)
                .padding(paddingValues)
        ) {
            if (loading) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(darkBlue)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Loading()
                }
            } else if(history.isNotEmpty()) Column(
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
                    percentage = percentages.humanas.toFloat(),
                    modifier = Modifier
                        .size(size = 80.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Score(
                    text = "Linguagens e suas Tecnologias",
                    percentage = percentages.linguagens.toFloat(),
                    modifier = Modifier
                        .size(size = 80.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Score(
                    text = "Ciencias Naturais e suas Tecnologias",
                    percentage = percentages.natureza.toFloat(),
                    modifier = Modifier
                        .size(size = 80.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Score(
                    text = "Matemática e suas Tecnologias",
                    percentage = percentages.matematica.toFloat(),
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
                history.map {
                    HistoryItem(
                        click = { navController.navigate("${Screen.QuestionInfoScreen.route}/${it.question?.url}") },
                        myColor = if (it.correct == true) {
                            green
                        } else {
                            red
                        }, text = if (it.question?.name == null) {
                            ""
                        } else {
                            it.question.name
                        }
                    )
                }
                if (feedback.isNotEmpty()) {
                    MyAlertDialog(feedback) { historyViewModel.resetFeedback() }
                }
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryButton(text = "Deletar histórico", click = {
                    historyViewModel.eraseHistory()
                })
                Spacer(
                    modifier = Modifier
                        .height(96.dp)
                )
            } else Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(darkBlue)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Seu histórico está vazio.",
                    color = white,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun HistoryItem(myColor: Color, text: String, click: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .border(
                border = BorderStroke(2.dp, myColor),
                shape = RoundedCornerShape(5.dp),
            )
            .fillMaxWidth()
            .clickable { click() }
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
        modifier = Modifier.fillMaxWidth(),
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