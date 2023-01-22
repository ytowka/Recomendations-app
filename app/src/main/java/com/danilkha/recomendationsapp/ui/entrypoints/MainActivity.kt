package com.danilkha.recomendationsapp.ui.entrypoints

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.danilkha.recomendationsapp.ui.auth.AuthViewModel

import com.danilkha.recomendationsapp.ui.theme.RecomendationsAppTheme
import com.danilkha.recomendationsapp.ui.utils.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecomendationsAppTheme {
                AppNavHost()
            }
        }
    }
}





















