package com.br.iluminar.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.br.iluminar.R
import com.br.iluminar.domain.model.Task
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.presentation.screens.Screen
import com.br.iluminar.presentation.viewmodels.AddTaskViewModel
import com.google.firebase.Timestamp
import java.sql.Date
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun AddTaskScreen(navController: NavController, viewModel: AddTaskViewModel) {
    val fontFamily = FontFamily(
        Font(R.font.dancing_script_bold_compose)
    )

    var resultObserver = viewModel.resource.observeAsState().value
    var showLoading by remember { mutableStateOf(false) }
    var isNavigationDone by remember { mutableStateOf(false) }
    var isSaveButtonClicked by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedColor = Color.Blue
    var date by remember { mutableStateOf(LocalDate.now()) }
    var startTime by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var endTime by remember { mutableStateOf(LocalTime.of(17, 0)) }

    val colors = listOf(Color.Blue, Color.Yellow, Color.Cyan, Color.Green, Color.Magenta)
    val context = LocalContext.current
    val lifecycle = context as LifecycleOwner

    if (isSaveButtonClicked) {
        when (resultObserver) {
            is Resource.Loading -> {
                showLoading = true
                isNavigationDone = false
            }
            is Resource.Success -> {
                if (!isNavigationDone) {
                    isNavigationDone = true
                    isSaveButtonClicked = false
                    navController.navigate(Screen.DailyActivitiesScreen.route)
                }
            }
            else -> {}
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(Color.White)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Adicionar uma atividade",
                color = Color(0xFFF1884D),
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                fontFamily = fontFamily,

                )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                maxLines = 10,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Column() {
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(5.dp),
                        textAlign = TextAlign.Start,
                        text = "Data:",
                        fontSize = 18.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clickable {
                                viewModel.showDatePicker(context)
                                viewModel.selectedDate.observe(
                                    lifecycle
                                ) {
                                    date = it.time
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                }
                            },
                        textAlign = TextAlign.Start,
                        text = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        fontSize = 18.sp
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(5.dp),
                        textAlign = TextAlign.Start,
                        text = "Horário de início:",
                        fontSize = 18.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clickable {
                                viewModel.showStartTimePicker(context)
                                viewModel.startTime.observe(
                                    lifecycle
                                ) {
                                    startTime = it
                                }
                            },
                        textAlign = TextAlign.Start,
                        text = "$startTime",
                        fontSize = 18.sp
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(5.dp),
                        textAlign = TextAlign.Start,
                        text = "Horário de término:",
                        fontSize = 18.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clickable {
                                viewModel.showEndTimePicker(context)
                                viewModel.endTime.observe(
                                    lifecycle
                                ) {
                                    endTime = it
                                }
                            },
                        textAlign = TextAlign.Start,
                        text = "$endTime",
                        fontSize = 18.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    colors.forEach { color ->
                        IconButton(
                            onClick = {
                                selectedColor = color
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .background(shape = CircleShape, color = color)
                                .padding(8.dp),

                            ) {
                        }
                    }
                }
                Button(
                    onClick = {
                        val newTask =
                            Task(
                                title,
                                description,
                                Timestamp(
                                    Date.from(
                                        date.atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    )
                                ),
                                startTime.toString(),
                                endTime.toString(),
                                selectedColor
                            )
                        if (newTask.title.isEmpty()) {
                            Toast.makeText(
                                context,
                                "O título não pode ser vazio",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else if (LocalTime.parse(newTask.startTime)
                                .isAfter(LocalTime.parse(newTask.endTime))
                        ) {
                            Toast.makeText(
                                context,
                                "O horário de início não pode ser após o horário de término",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            viewModel.addTask(newTask)
                            isSaveButtonClicked = true
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF1884D))

                ) {
                    Text(
                        text = "Salvar",
                        style = MaterialTheme.typography.button
                    )
                }
                if (showLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFF1884D))
                    }
                }
            }


        }
    }

}

