package com.br.iluminar.domain.model

import androidx.compose.ui.graphics.Color
import com.google.firebase.Timestamp

data class Activity(
    val title: String,
    val description: String?,
    val date: Timestamp,
    val startTime: String,
    val endTime: String,
    val color: Color?
) {
    constructor() : this(
        title = "dummy title",
        description = "dummy description",
        date = Timestamp.now(),
        "",
        "",
        color = Color.White
    )
}
