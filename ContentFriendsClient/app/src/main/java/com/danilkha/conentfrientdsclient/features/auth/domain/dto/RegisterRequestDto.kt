package com.danilkha.conentfrientdsclient.features.auth.domain.dto

import android.net.Uri

data class RegisterRequestDto(
    val username: String,
    val avatarUri: Uri?,
    val email: String,
    val phone: String,
    val fullName: String,
    val password: String,
)