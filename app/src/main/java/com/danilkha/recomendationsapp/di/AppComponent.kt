package com.danilkha.recomendationsapp.di

import com.danilkha.recomendationsapp.ui.auth.AuthViewModel
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [ViewModelModule::class]
)
@Singleton
interface AppComponent{

    fun authViewModel(): AuthViewModel
}