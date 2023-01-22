package com.danilkha.recomendationsapp.domain

interface UserRepository {
    fun isAuthorized(): Boolean

    class Impl() : UserRepository{
        override fun isAuthorized(): Boolean {
            return true
        }
    }
}