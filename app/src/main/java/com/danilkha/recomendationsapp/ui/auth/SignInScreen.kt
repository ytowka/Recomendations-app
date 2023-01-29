package com.danilkha.recomendationsapp.ui.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danilkha.recomendationsapp.R
import com.danilkha.recomendationsapp.ui.utils.LaunchCollectEffects
import com.danilkha.recomendationsapp.ui.utils.getViewModel
import com.danilkha.recomendationsapp.ui.utils.injector


@Composable
fun SignInScreen(
    onLoginSucceed: () -> Unit,
    viewModel: AuthViewModel = getViewModel { injector.authViewModel() } ,
){
    val state = viewModel.state.collectAsState()
    viewModel.LaunchCollectEffects{ effect ->
        when(effect){
            SignInSideEffect.SignInSucceed -> onLoginSucceed()
        }
    }



    val authLaunch = rememberLauncherForActivityResult(GoogleAuthContract()){
        if(it is GoogleAuthResult.Ok){
            viewModel.obtainEvent(SignInUserEvent.GoogleSignInSucceed(it.account))
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                authLaunch.launch(state.value.googleSignInOptions)
            }
        ){
            Image(
                painter = painterResource(R.drawable.ic_google_g),
                contentDescription = null
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 100.dp)
                .align(alignment = Alignment.Center),
            value = "text",
            onValueChange = {}
        )
    }
}
