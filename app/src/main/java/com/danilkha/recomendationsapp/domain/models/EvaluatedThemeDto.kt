package com.danilkha.recomendationsapp.domain.models

data class EvaluatedThemeDto(
    val themeDto: ThemeDto,
    val values: List<EvaluatedObjectDto>,
)