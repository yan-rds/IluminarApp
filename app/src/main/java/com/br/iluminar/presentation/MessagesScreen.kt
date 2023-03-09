package com.br.iluminar.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.iluminar.R
import com.br.iluminar.domain.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MessagesScreen() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                MessagesFullScreen()
            }
        }
    }
}

@Composable
fun MessagesFullScreen() {
    val fontFamily = FontFamily(
        Font(R.font.dancing_script_bold_compose)
    )
    Column {
        MessageTitle(fontFamily = fontFamily)
        ContactThroughWhatsApp()
        TextMessageBox()
        MessageBox()
    }

}

@Composable
fun MessageTitle(fontFamily: FontFamily) {
    Text(
        text = "Mensagens",
        fontFamily = fontFamily,
        fontSize = 40.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .background(Color.White),
        color = Color(0xFFF1884D)
    )
}

@Composable
fun ContactThroughWhatsApp() {
    val phoneNumber = "+5511971238853"
    val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = {
                context.startActivity(intent)
            }, modifier = Modifier.padding(start = 30.dp)
        ) {
            val image: Painter = painterResource(id = R.drawable.icon_zap)
            Image(
                image, contentDescription = "zap", modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)

            )
        }
        Text(
            text = "Fale com a gente pelo nosso whatsApp",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 10.dp)
        )
    }
}

@Composable
fun TextMessageBox() {
    Text(
        text = "Ou se preferir, pode enviar um recado ou sugestão pela caixinha abaixo",
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(start = 70.dp, end = 70.dp, top = 40.dp)
            .fillMaxWidth()
    )
}

@Composable
fun MessageBox() {
    var content by remember { mutableStateOf("") }
    val fireStore = FirebaseFirestore.getInstance()
    val myRef = fireStore.collection("Mensagens")
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(40.dp)
            .border(
                border = BorderStroke(3.dp, color = Color(0xFFF1884D)),
                RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))

    ) {
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Deixe sua mensagem") },
            maxLines = 30,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color(0x66F578E9))
                .padding(top = 10.dp)
        )
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                val message = Message(
                    content = content,
                    authorId = userId
                )
                if (content.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Não é possível enviar uma mensagem vazia",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, "Mensagem enviada", Toast.LENGTH_SHORT).show()
                    myRef.add(message)
                    content = ""
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xA6F1884D)),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Salvar", color = Color(0xCC181818))
        }
    }
}
