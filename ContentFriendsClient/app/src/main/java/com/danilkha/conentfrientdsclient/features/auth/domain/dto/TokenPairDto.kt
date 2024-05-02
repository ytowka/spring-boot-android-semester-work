package com.danilkha.conentfrientdsclient.features.auth.domain.dto

data class TokenPairDto(
    val accessToken: String,
    val refreshToken: String
)