package com.danilkha.recomendationsapp.entrypoints

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danilkha.recomendationsapp.ui.auth.AuthViewModel

import com.danilkha.recomendationsapp.ui.theme.RecomendationsAppTheme
import com.danilkha.recomendationsapp.ui.utils.*
import com.danilkha.recomendationsapp.utils.injector
import com.danilkha.recomendationsapp.utils.rememberViewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel by rememberViewModel { injector.appViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel

        setContent {
            RecomendationsAppTheme {
                AppNavHost()
            }
        }
    }
}





















