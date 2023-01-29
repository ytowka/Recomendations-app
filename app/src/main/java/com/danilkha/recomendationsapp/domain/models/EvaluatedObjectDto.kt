package com.danilkha.recomendationsapp.domain.models

data class EvaluatedObjectDto(
    val objectDto: ObjectDto,
    val value: Float?,
)
