package com.danilkha.recomendationsapp.ui.auth

import android.app.Activity
import android.util.Log
import com.danilkha.recomendationsapp.domain.FirebaseAuthRepository
import com.danilkha.recomendationsapp.domain.UserRepository
import com.danilkha.recomendationsapp.ui.utils.MviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
): MviViewModel<SignInState, SignInEvent, SignInUserEvent, SignInSideEffect>(){

    override val startState = SignInState()

    override fun reduce(currentState: SignInState, event: SignInEvent): SignInState {
        return super.reduce(currentState, event)
    }

    override suspend fun onSideEffect(
        prevState: SignInState,
        newState: SignInState,
        event: SignInEvent
    ) {
        when(event){
            is SignInUserEvent.GoogleSignInSucceed -> {
                withContext(Dispatchers.IO){
                    firebaseAuthRepository.login(event.googleSignInAccount)
                    showSideEffect(SignInSideEffect.SignInSucceed)
                }
            }
        }
    }

}