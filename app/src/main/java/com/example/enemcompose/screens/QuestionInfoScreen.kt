package com.example.enemcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.enemcompose.components.Loading
import com.example.enemcompose.components.MyBottomNavigation
import com.example.enemcompose.factories.QuestionInfoViewModelFactory
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.white
import com.example.enemcompose.utils.StringHandler
import com.example.enemcompose.view.model.QuestionInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionInfoScreen(navController: NavController, id: String) {
    val questionInfoViewModel: QuestionInfoViewModel =
        viewModel(factory = QuestionInfoViewModelFactory(id))

    val feedback by questionInfoViewModel.feedback.collectAsState()
    val uiState by questionInfoViewModel.uiState.collectAsState()
    val loading by questionInfoViewModel.loading.collectAsState()

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
            if (loading)
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
            if (feedback != "") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(darkBlue)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = feedback,
                        color = white,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
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
                    isWrong = uiState.rightAnswer != a
                )
                AnswerItem(
                    msg = uiState.answers.b,
                    isWrong = uiState.rightAnswer != b
                )
                AnswerItem(
                    msg = uiState.answers.c,
                    isWrong = uiState.rightAnswer != c,
                )
                AnswerItem(
                    msg = uiState.answers.d,
                    isWrong = uiState.rightAnswer != d,
                )
                AnswerItem(
                    msg = uiState.answers.e,
                    isWrong = uiState.rightAnswer != e,
                )
            }
        }
    }
}