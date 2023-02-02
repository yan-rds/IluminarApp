package com.br.iluminar.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.br.iluminar.Navigation
import com.br.iluminar.R
import com.br.iluminar.Screen
import com.br.iluminar.model.Activity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DailyActivitiesScreen() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigation()
        }
    }
}

@Composable
fun DailyActivitiesFullScreen(navController: NavController?) {

    val fontFamily = FontFamily(
        Font(R.font.dancing_script_bold_compose)
    )

    val list = updateList()

    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        ) {
            Title(fontFamily)
            AddButton(navController)
        }
        ActivitiesList(list)
        SeeFullAgendaText()
        Footer(
            painter = painterResource(id = R.drawable.children_footer),
            contentDescription = "footer"
        )
    }
}

@Composable
private fun Title(
    fontFamily: FontFamily,
    modifier: Modifier = Modifier

) {

    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 30.dp, bottom = 40.dp)
    ) {
        Text(
            modifier = modifier
                .width(280.dp),
            text = "Programação",
            color = Color(0xFFF1884D),
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontFamily = fontFamily,

            )
    }
}

@Composable
fun updateList(): List<Activity> {
    val tasks = mutableListOf<Activity>()

    val tasksCollection = FirebaseFirestore.getInstance().collection("Atividades").document().get()
    Log.i("doc", tasksCollection.toString())

    FirebaseFirestore.getInstance().collection("Atividades")
        .addSnapshotListener { snapshot, exception ->
            if (exception != null) {

            }
            snapshot?.documents?.forEach { document ->
                Log.i("forEachDoc", document.toString())
                val task = document.toObject(Activity::class.java)
                if (task != null) {
                    tasks.add(task)
                }
            }

        }
    return tasks
}

@Composable
fun ActivitiesList(list: List<Activity>) {

    var showList by remember { mutableStateOf(false) }
    LaunchedEffect(showList) {
        delay(1000)
        showList = true
    }

    if (showList) {
        val today = LocalDate.now()
        val todayDate: Date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val filteredList = list.filter { task ->
            task.date.toDate().toString() == todayDate.toString()
        }
        val sortedList = filteredList.sortedWith(compareBy { it.startTime })


        Box(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .background(Color.White),
                contentAlignment = Alignment.TopCenter

            ) {
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(sortedList.size) {
                        val currentActivity = sortedList[it]
                        TaskItem(activity = currentActivity)
                    }
                }
                Log.i("lista", "tamanho ${sortedList.size}")
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentAlignment = Center
        ) {
            CircularProgressIndicator(color = Color(0xFFF1884D))
        }
    }
}


@Composable
fun TaskItem(activity: Activity) {
    Box(
        modifier = Modifier
            .border(1.dp, Color(0xFFF1884D), shape = RoundedCornerShape(30.dp))
            .height(50.dp)
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 30.dp),
                text = "${activity.startTime.format(DateTimeFormatter.ofPattern("hh:MM"))} - ${
                    activity.endTime.format(
                        DateTimeFormatter.ofPattern("hh:MM")
                    )
                }",
                fontSize = 13.sp,
                color = Color(0xFFF1884D)
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 30.dp),
                text = activity.title,
                fontSize = 18.sp,
                color = Color(0xFFF1884D)
            )
        }
    }
    Spacer(
        modifier = Modifier
            .height(10.dp)
    )

}

@Composable
fun SeeFullAgendaText() {
    val context = LocalContext.current
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                Toast
                    .makeText(context, "ver agenda completa", Toast.LENGTH_SHORT)
                    .show()
            },
        text = "Ver agenda completa",
        color = Color(0xFFF1884D),
        textAlign = TextAlign.End
    )
}

@Composable
fun AddButton(navController: NavController?) {


    Scaffold(topBar = { },
        modifier = Modifier.padding(bottom = 40.dp, end = 30.dp),
        drawerContentColor = Color(0xFFF1884D),
        drawerBackgroundColor = Color(0xFFF1884D),

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController?.navigate(Screen.AddActivityScreen.route) }
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }, content = {
        })

}

@Composable
fun Footer(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = BottomCenter
    ) {
        Card(
            modifier = modifier
                .width(430.dp)
                .height(130.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}