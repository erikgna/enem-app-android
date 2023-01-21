package com.example.enemcompose.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.enemcompose.Logo
import com.example.enemcompose.components.MyBottomNavigation
import com.example.enemcompose.components.PrimaryButton
import com.example.enemcompose.components.SecondaryButton
import com.example.enemcompose.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.enemcompose.factories.LoginViewModelFactory
import com.example.enemcompose.factories.QuestionViewModelFactory
import com.example.enemcompose.utils.StringHandler
import com.example.enemcompose.view.model.LoginViewModel
import com.example.enemcompose.view.model.QuestionViewModel

const val http = "http"
const val a = "a"
const val b = "b"
const val c = "c"
const val d = "d"
const val e = "e"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(navController: NavController) {
    val questionViewModel: QuestionViewModel =
        viewModel(factory = QuestionViewModelFactory(LocalContext.current))
    val uiState by questionViewModel.uiState.collectAsState()

    val choosen = remember { mutableStateOf("") }
    val isAnswer = remember { mutableStateOf(true) }

    fun checkAnswer() {
        questionViewModel.addQuestion(choosen.value == uiState.rightAnswer)
    }

    fun nextQuestion() {
        questionViewModel.getQuestion()
    }

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(darkBlue)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Logo()
                Spacer(modifier = Modifier.height(40.dp))
                if (uiState.name != null) {
                    Text(
                        text = uiState.name!!,
                        color = white,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                uiState.description?.let {
                    StringHandler.transformTextToWidget(it).forEach { item ->
                        if (item.contains(http)) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(300.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(item)
                                        .crossfade(true).build(),
                                    contentScale = ContentScale.FillBounds,
                                    contentDescription = "Image"
                                )
                            }
                        } else {
                            Text(text = item, color = white, fontSize = 16.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                uiState.ask?.let {
                    StringHandler.transformTextToWidget(it).forEach { item ->
                        if (item.contains(http)) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(300.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(item)
                                        .crossfade(true).build(),
                                    contentScale = ContentScale.FillBounds,
                                    contentDescription = "Image"
                                )
                            }
                        } else {
                            Text(text = item, color = white, fontSize = 16.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))

                uiState.answers?.let {
                    AnswerItem(
                        msg = it.a,
                        isWrong = uiState.rightAnswer != a,
                        isAnswer = isAnswer.value,
                        click = { choosen.value = a })
                }
                uiState.answers?.let {
                    AnswerItem(
                        msg = it.b,
                        isWrong = uiState.rightAnswer != b,
                        isAnswer = isAnswer.value,
                        click = { choosen.value = b })
                }
                uiState.answers?.let {
                    AnswerItem(
                        msg = it.c,
                        isWrong = uiState.rightAnswer != c,
                        isAnswer = isAnswer.value,
                        click = { choosen.value = c })
                }
                uiState.answers?.let {
                    AnswerItem(
                        msg = it.d,
                        isWrong = uiState.rightAnswer != d,
                        isAnswer = isAnswer.value,
                        click = { choosen.value = d })
                }
                uiState.answers?.let {
                    AnswerItem(
                        msg = it.e,
                        isWrong = uiState.rightAnswer != e,
                        isAnswer = isAnswer.value,
                        click = { choosen.value = e })
                }

                Spacer(modifier = Modifier.height(16.dp))
                PrimaryButton(text = "Ver resposta", click = { checkAnswer() })
                Spacer(modifier = Modifier.height(8.dp))
                SecondaryButton(text = "Próxima pergunta", click = { nextQuestion() })
            }
        }
    }
}

@Composable
fun AnswerItem(msg: String, isWrong: Boolean, isAnswer: Boolean, click: () -> Unit) {
    Box(
        contentAlignment = if (msg.contains(http)) {
            Alignment.Center
        } else {
            Alignment.CenterStart
        },
        modifier = if (isAnswer) {
            Modifier
                .padding(bottom = 24.dp)
                .border(
                    border = BorderStroke(2.dp, primaryBlue),
                    shape = RoundedCornerShape(4.dp),
                )
                .fillMaxWidth()
                .clickable { click() }
        } else if (isWrong) {
            Modifier
                .padding(bottom = 24.dp)
                .border(
                    border = BorderStroke(2.dp, red),
                    shape = RoundedCornerShape(4.dp),
                )
                .fillMaxWidth()
                .clickable { click() }
        } else {
            Modifier
                .padding(bottom = 24.dp)
                .border(
                    border = BorderStroke(2.dp, green),
                    shape = RoundedCornerShape(4.dp),
                )
                .fillMaxWidth()
                .clickable { click() }
        }
    ) {
        if (msg.contains(http)) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(16.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(msg.substring(10, msg.indexOf(">") - 3))
                    .crossfade(true).build(),
                contentScale = ContentScale.FillBounds,
                contentDescription = "Image"
            )
        } else {
            Text(
                text = msg,
                color = white,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
        }
    }
}