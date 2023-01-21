package com.example.enemcompose.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.enemcompose.*
import com.example.enemcompose.components.MyBottomNavigation
import com.example.enemcompose.components.PrimaryButton
import com.example.enemcompose.components.SecondaryButton
import com.example.enemcompose.factories.QuestionViewModelFactory
import com.example.enemcompose.ui.theme.darkBlue
import com.example.enemcompose.ui.theme.primaryBlue
import com.example.enemcompose.ui.theme.secondaryBlue
import com.example.enemcompose.ui.theme.white
import com.example.enemcompose.view.model.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val areas = remember { mutableStateListOf<String>() }
    val years = remember { mutableStateListOf<String>() }

    fun editArea(area: String) {
        if (areas.contains(area)) {
            areas.remove(area)
        } else {
            areas.add(area)
        }
    }

    fun editYear(year: String) {
        if (years.contains(year)) {
            years.remove(year)
        } else {
            years.add(year)
        }
    }

    fun navigateToQuestion() {
        navController.navigate("${Screen.QuestionScreen.route}/${areas}/${years}")
    }

    fun navigateToRandomQuestion() {
        navController.navigate("${Screen.QuestionScreen.route}/[]/[]")
    }

    Scaffold(
        bottomBar = {
            Column() {
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
                    Spacer(modifier = Modifier.height(48.dp))
                    Text(
                        text = "Selecione as áreas desejadas",
                        color = white,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Left
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    CheckArea(
                        text = "Ciências Humanas\ne suas Tecnologias",
                        icon = Icons.Rounded.Add,
                        click = { editArea("humanas") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CheckArea(
                        text = "Matemática e suas\n Tecnologias",
                        icon = Icons.Rounded.Add,
                        click = { editArea("matematica") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CheckArea(
                        text = "Ciências da Natureza\n e suas Tecnologias",
                        icon = Icons.Rounded.Add,
                        click = { editArea("natureza") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CheckArea(
                        text = "Linguagens, Códigos\ne suas Tecnologias",
                        icon = Icons.Rounded.Add,
                        click = { editArea("linguagens") }
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    Text(
                        modifier = Modifier.align(Alignment.Start),
                        text = "Selecione os anos desejados",
                        color = white,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Left
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row() {
                        Column() {
                            CheckYear(text = "2011", click = { editYear("2011") })
                            CheckYear(text = "2013", click = { editYear("2013") })
                            CheckYear(text = "2015", click = { editYear("2015") })
                            CheckYear(text = "2017", click = { editYear("2017") })
                            CheckYear(text = "2019", click = { editYear("2019") })
                            CheckYear(text = "2022", click = { editYear("2022") })
                        }
                        Spacer(modifier = Modifier.width(32.dp))
                        Column() {
                            CheckYear(text = "2012", click = { editYear("2012") })
                            CheckYear(text = "2014", click = { editYear("2014") })
                            CheckYear(text = "2016", click = { editYear("2016") })
                            CheckYear(text = "2018", click = { editYear("2018") })
                            CheckYear(text = "2020", click = { editYear("2020") })
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton(text = "Gerar Perguntas", click = { navigateToQuestion() })
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(
                        text = "Gerar Perguntas Aleatoriamente",
                        click = { navigateToRandomQuestion() })
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
fun CheckYear(text: String, click: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            isClicked = !isClicked
            click()
        }
    ) {
        Checkbox(
            checked = isClicked,
            onCheckedChange = { isClicked = !isClicked },
            colors = CheckboxDefaults.colors(
                checkmarkColor = white,
                uncheckedColor = white,
                checkedColor = primaryBlue
            )
        )
        Text(text = text, color = white, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

@Composable
fun CheckArea(text: String, icon: ImageVector, click: () -> Unit) {
    var isClicked by remember { mutableStateOf(true) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = if (isClicked) {
            Modifier
                .border(
                    border = BorderStroke(2.dp, primaryBlue),
                    shape = RoundedCornerShape(16.dp),
                )
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .clickable {
                    isClicked = false
                    click()
                }
        } else {
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            primaryBlue,
                            secondaryBlue
                        )
                    )
                )
                .padding(vertical = 24.dp)
                .clickable {
                    isClicked = true
                    click()
                }
        }
    ) {
        Icon(
            icon,
            contentDescription = text,
            tint = white
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = white
        )
    }
}