package com.danilkha.recomendationsapp.ui.utils

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner


@Composable
inline fun<reified T : ViewModel> getViewModel(crossinline getInstance: () -> T) : T {
    return ViewModelProvider(
        owner = LocalViewModelStoreOwner.current!!,
        factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return getInstance() as T
            }
        }
    )[T::class.java]
}

@Composable
inline fun<reified T : ViewModel> getActivityViewModel(crossinline getInstance: () -> T) : T {
    return ViewModelProvider(LocalContext.current.findActivity(), factory = object : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return getInstance() as T
        }
    })[T::class.java]
}

inline fun<reified T: ViewModel> ViewModelStoreOwner.rememberViewModel(crossinline getInstance: () -> T): Lazy<T> = lazy {
    ViewModelProvider(this, factory = object : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return getInstance() as T
        }
    })[T::class.java]
}
