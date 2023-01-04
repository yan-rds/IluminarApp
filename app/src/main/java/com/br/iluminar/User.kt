package com.br.iluminar

import android.net.Uri

data class User(
    val name:String,
    val period:String,
    val schoolYear:Int,
    val responsibleName:String,
    val responsiblePhone:String,
    val profileUri: String
)
