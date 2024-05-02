package com.danilkha.contentfriends

import kotlinx.serialization.Serializable

@Serializable
data class ExceptionResponse(
    val status: Int,
    val error: String,
    val message: String?,
    val path: String
) {
}