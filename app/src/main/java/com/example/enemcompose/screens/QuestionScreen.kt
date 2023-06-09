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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.enemcompose.Logo
import com.example.enemcompose.Screen
import com.example.enemcompose.components.*
import com.example.enemcompose.factories.QuestionViewModelFactory
import com.example.enemcompose.ui.theme.*
import com.example.enemcompose.utils.StringHandler
import com.example.enemcompose.view.model.QuestionViewModel

const val http = "http"
const val a = "a"
const val b = "b"
const val c = "c"
const val d = "d"
const val e = "e"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(navController: NavController, random: Boolean) {
    val questionViewModel: QuestionViewModel =
        viewModel(factory = QuestionViewModelFactory(LocalContext.current, random))

    val uiState by questionViewModel.uiState.collectAsState()

    val error by questionViewModel.error.collectAsState()
    val choosen = remember { mutableStateOf("") }
    val isAnswer = remember { mutableStateOf(true) }
    val loading by questionViewModel.loading.collectAsState()

    fun checkAnswer() {
        if(choosen.value.isNotEmpty()){
            questionViewModel.addQuestion(choosen.value == uiState.rightanswer, choosen = choosen.value)
            isAnswer.value = false
        }
    }

    fun nextQuestion() {
        choosen.value = ""
        isAnswer.value = true
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
            } else Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(darkBlue)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Logo()
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = uiState.name,
                    color = white,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(32.dp))
                uiState.description.let {
                    StringHandler.transformTextToWidget(it).forEach { item ->
                        if (item.contains(http)) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            ) {
                                SubcomposeAsyncImage(
                                    loading = {
                                        Loading()
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(8.dp)),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(item)
                                        .crossfade(true).build(),
                                    contentScale = ContentScale.FillBounds,
                                    contentDescription = "Image",

                                    )
                            }
                        } else {
                            Text(text = item.replace("<strong>", "").replace("</strong>", "").replace("<br />", ""), color = white, fontSize = 16.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                uiState.ask.let {
                    StringHandler.transformTextToWidget(it).forEach { item ->
                        if (item.contains(http)) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            ) {
                                SubcomposeAsyncImage(
                                    loading = {
                                        Loading()
                                    },
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

                AnswerItem(
                    msg = uiState.answers.a,
                    isWrong = uiState.rightanswer != a,
                    isAnswer = isAnswer.value,
                    click = { choosen.value = a },
                    selected = choosen.value == a,
                )
                AnswerItem(
                    msg = uiState.answers.b,
                    isWrong = uiState.rightanswer != b,
                    isAnswer = isAnswer.value,
                    selected = choosen.value == b,
                    click = { choosen.value = b })
                AnswerItem(
                    msg = uiState.answers.c,
                    isWrong = uiState.rightanswer != c,
                    isAnswer = isAnswer.value,
                    selected = choosen.value == c,
                    click = { choosen.value = c })
                AnswerItem(
                    msg = uiState.answers.d,
                    isWrong = uiState.rightanswer != d,
                    isAnswer = isAnswer.value,
                    selected = choosen.value == d,
                    click = { choosen.value = d })
                AnswerItem(
                    msg = uiState.answers.e,
                    isWrong = uiState.rightanswer != e,
                    isAnswer = isAnswer.value,
                    selected = choosen.value == e,
                    click = { choosen.value = e })

                Spacer(modifier = Modifier.height(16.dp))
                PrimaryButton(text = "Ver resposta", click = { checkAnswer() })
                Spacer(modifier = Modifier.height(8.dp))
                SecondaryButton(text = "Próxima pergunta", click = {
                    nextQuestion()
                })

                if (error.isNotEmpty()) {
                    MyAlertDialog(error) {
                        questionViewModel.resetError()
                        if (error.contains("rro")) {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(Screen.QuestionScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnswerItem(
    msg: String,
    isWrong: Boolean,
    isAnswer: Boolean = false,
    click: () -> Unit = {},
    selected: Boolean = false
) {
    Box(
        contentAlignment = if (msg.contains(http)) {
            Alignment.Center
        } else {
            Alignment.CenterStart
        },
        modifier = if (selected && isAnswer) {
            Modifier
                .padding(bottom = 24.dp)
                .border(
                    border = BorderStroke(2.dp, primaryBlue),
                    shape = RoundedCornerShape(4.dp),
                )
                .fillMaxWidth()
                .clickable { click() }
        } else if (isAnswer) {
            Modifier
                .padding(bottom = 24.dp)
                .border(
                    border = BorderStroke(2.dp, white),
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
            SubcomposeAsyncImage(
                loading = {
                    Loading()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .aspectRatio(1f)
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