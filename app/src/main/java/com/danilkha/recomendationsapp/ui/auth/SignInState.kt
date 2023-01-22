package com.danilkha.recomendationsapp.ui.auth

import androidx.compose.runtime.Immutable
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Immutable
data class SignInState(
    val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("1059892605990-mivqdskq4t33895ta5ht2l42l3q1g289.apps.googleusercontent.com")
        .requestEmail()
        .build(),
)

sealed interface SignInEvent{

}

sealed interface SignInUserEvent : SignInEvent{
    data class GoogleSignInSucceed(val googleSignInAccount: GoogleSignInAccount) : SignInUserEvent
}

sealed interface SignInSideEffect{
    object SignInSucceed : SignInSideEffect
}
