package com.danilkha.contentfriends.api

import kotlinx.serialization.Serializable

@Serializable
data class ExceptionModel(
    val status: Int,
    val name: String,
    val message: String,
)
