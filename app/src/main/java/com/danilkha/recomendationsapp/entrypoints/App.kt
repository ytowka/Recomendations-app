package com.danilkha.recomendationsapp.entrypoints

import android.app.Application
import android.util.Log
import com.danilkha.recomendationsapp.di.DaggerAppComponent
import com.danilkha.recomendationsapp.di.AppComponent
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize


class App : Application(){
    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }

    override fun onCreate() {
        super.onCreate()
        Log.i("TAG", "onCreate: ")
        Firebase.initialize(this)
    }
}