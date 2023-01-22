package com.danilkha.recomendationsapp.di

import android.content.Context
import com.danilkha.recomendationsapp.domain.FirebaseAuthRepository
import com.danilkha.recomendationsapp.domain.UserRepository
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideUserRepository(): UserRepository{
        return UserRepository.Impl();
    }

    @Provides
    fun provideFirebaseAuthRepository(): FirebaseAuthRepository{
        return FirebaseAuthRepository.Impl()
    }
}