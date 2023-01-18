package com.br.iluminar.presentation

import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
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
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.iluminar.R
import com.br.iluminar.presentation.ui.theme.IluminarTheme

class DailyActivitiesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fontFamily = FontFamily(
            Font(R.font.dancing_script_bold_compose)
        )
        setContent {
            Column(
                modifier = Modifier
                    .background(Color.White)
            ) {
                Title(fontFamily)
                ActivitiesList()
                SeeFullAgendaText()
                Footer(
                    painter = painterResource(id = R.drawable.children_footer),
                    contentDescription = "footer"
                )
            }


        }
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
                .fillMaxWidth(),
            text = "Programação",
            color = Color(0xFFF1884D),
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontFamily = fontFamily,

        )
    }
}

@Composable
private fun ActivitiesList() {
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
                items(8) {
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
                                text = "08:00 - 12:00",
                                fontSize = 13.sp,
                                color = Color(0xFFF1884D)
                            )
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 10.dp, horizontal = 30.dp),
                                text = "Brincadeiras",
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
            }
        }
    }
}

@Composable
private fun SeeFullAgendaText() {
    val context = LocalContext.current
    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp).clickable {
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
private fun Footer(
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


