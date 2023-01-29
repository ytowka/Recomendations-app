package com.danilkha.recomendationsapp.di

import com.danilkha.recomendationsapp.domain.repos.FirebaseAuthRepository
import com.danilkha.recomendationsapp.domain.repos.UserRepository
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideUserRepository(): UserRepository {
        return UserRepository.Impl();
    }

    @Provides
    fun provideFirebaseAuthRepository(): FirebaseAuthRepository {
        return FirebaseAuthRepository.Impl()
    }
}