package com.danilkha.recomendationsapp.entrypoints

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danilkha.recomendationsapp.ui.auth.SignInScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController, "signin"){
        composable("signin"){
            SignInScreen(
                onLoginSucceed = {}
            )
        }
    }
}