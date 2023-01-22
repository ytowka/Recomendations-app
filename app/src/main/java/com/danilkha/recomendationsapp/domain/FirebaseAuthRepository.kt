package com.danilkha.recomendationsapp.domain

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface FirebaseAuthRepository {

    suspend fun login(googleSignInAccount: GoogleSignInAccount)
    suspend fun getCurrentUser(): FirebaseUser?

    class Impl : FirebaseAuthRepository{
        override suspend fun login(googleSignInAccount: GoogleSignInAccount) {
            Log.i("TAG", "parseResult: ${googleSignInAccount.idToken}")
            val credentials = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
            val task = Firebase.auth.signInWithCredential(credentials).await()
        }

        override suspend fun getCurrentUser(): FirebaseUser? {
            return Firebase.auth.currentUser
        }
    }
}