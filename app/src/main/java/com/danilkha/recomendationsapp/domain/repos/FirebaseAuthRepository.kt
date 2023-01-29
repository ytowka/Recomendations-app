package com.danilkha.recomendationsapp.domain.repos

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface FirebaseAuthRepository {

    suspend fun login(googleSignInAccount: GoogleSignInAccount)
    fun getCurrentUser(): FirebaseUser?
    fun isAuthorized(): Boolean

    class Impl : FirebaseAuthRepository {
        override suspend fun login(googleSignInAccount: GoogleSignInAccount) {
            Log.i("TAG", "parseResult: ${googleSignInAccount.idToken}")
            val credentials = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
            val task = Firebase.auth.signInWithCredential(credentials).await()
        }

        override fun getCurrentUser(): FirebaseUser? {
            return Firebase.auth.currentUser
        }

        override fun isAuthorized(): Boolean {
            return Firebase.auth.currentUser != null
        }
    }
}