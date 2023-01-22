package com.danilkha.recomendationsapp.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleAuthContract :
    ActivityResultContract<GoogleSignInOptions, GoogleAuthResult>() {
    override fun createIntent(context: Context, input: GoogleSignInOptions): Intent {
        val signInClient = GoogleSignIn.getClient(context, input);
        return signInClient.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): GoogleAuthResult {

        return if (resultCode == Activity.RESULT_OK){
             try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                 if(task.isSuccessful){
                     GoogleAuthResult.Ok(task.result)
                 }else{
                     GoogleAuthResult.Error("task unsuccessful")
                 }
            }catch (e: ApiException){
                GoogleAuthResult.Error(e.toString())
            }
        }else GoogleAuthResult.Canceled
    }
}

sealed class GoogleAuthResult{
    data class Ok(val account: GoogleSignInAccount): GoogleAuthResult()
    object Canceled : GoogleAuthResult()
    data class Error(val message: String) : GoogleAuthResult()
}