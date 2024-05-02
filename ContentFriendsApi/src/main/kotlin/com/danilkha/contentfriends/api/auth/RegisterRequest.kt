package com.danilkha.contentfriends.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val login: String,
    val password: String,
    val email: String,
    val phone: String,
    val fullName: String,
);
