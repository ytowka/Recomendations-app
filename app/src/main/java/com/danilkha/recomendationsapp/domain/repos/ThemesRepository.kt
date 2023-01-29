package com.danilkha.recomendationsapp.domain.repos

import com.danilkha.recomendationsapp.domain.models.ThemeDto

interface ThemesRepository {
    suspend fun getAvailableThemes(): List<ThemeDto>


}