package com.danilkha.recomendationsapp.ui.entrypoints

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danilkha.recomendationsapp.ui.auth.SignInScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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