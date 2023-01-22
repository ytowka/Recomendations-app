package com.danilkha.recomendationsapp.ui.tests

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun Screen(){
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxSize()
        ,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background, textColor = MaterialTheme.colors.onBackground)
        )

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background, textColor = MaterialTheme.colors.onBackground)
        )

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background, textColor = MaterialTheme.colors.onBackground)
        )

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background, textColor = MaterialTheme.colors.onBackground)
        )
    }
}