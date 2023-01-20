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
import com.example.enemcompose.view.model.QuestionViewModel

data class Answer(val msg: String, val index: String)

fun transformTextToWidget(desc: String): ArrayList<String> {
    val splited = desc.split("<img ")
    val widgets = ArrayList<String>()

    splited.forEach {
        if (it.contains("src")) {
            widgets.add(it.substring(5, it.indexOf(">") - 3))
            widgets.add(it.substring(it.indexOf(">") + 1))
        } else {
            widgets.add(it.replace("<br />", "\n"))
        }
    }

    return widgets
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(navController: NavController, viewModel: QuestionViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            Column{
                Spacer(
                    modifier = Modifier
                        .background(white)
                        .fillMaxWidth()
                        .height(2.dp)
                )
                MyBottomNavigation(navController = navController)
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
                        transformTextToWidget(it).forEach { item ->
                            if (item.contains("http")) {
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
                        transformTextToWidget(it).forEach { item ->
                            if (item.contains("http")) {
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

                    uiState.answers?.let { AnswerItem(msg = it.a, isCorrect = false, isAnswer = true) }
                    uiState.answers?.let { AnswerItem(msg = it.b, isCorrect = false, isAnswer = true) }
                    uiState.answers?.let { AnswerItem(msg = it.c, isCorrect = false, isAnswer = true) }
                    uiState.answers?.let { AnswerItem(msg = it.d, isCorrect = false, isAnswer = true) }
                    uiState.answers?.let { AnswerItem(msg = it.e, isCorrect = false, isAnswer = true) }

                    Spacer(modifier = Modifier.height(16.dp))
                    PrimaryButton(text = "Ver resposta", click = { println(uiState.answers) })
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(text = "Próxima pergunta", click = {})
                }
            }
        })
}

@Composable
fun AnswerItem(msg: String, isCorrect: Boolean, isAnswer: Boolean) {
    Box(
        contentAlignment = if (msg.contains("http")) {
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
                .padding(vertical = 4.dp)
                .fillMaxWidth()
        } else if (isCorrect) {
            Modifier
                .padding(bottom = 8.dp)
                .border(
                    border = BorderStroke(2.dp, red),
                    shape = RoundedCornerShape(4.dp),
                )
                .fillMaxWidth()
        } else {
            Modifier
                .padding(bottom = 8.dp)
                .border(
                    border = BorderStroke(2.dp, green),
                    shape = RoundedCornerShape(4.dp),
                )
                .fillMaxWidth()
        }
    ) {
        if (msg.contains("http")) {
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