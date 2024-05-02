package com.danilkha.conentfrientdsclient.features.auth.domain.repository

class AccessTokenDto(
    val token: String,
    val expiresIn: Long,
) {
}