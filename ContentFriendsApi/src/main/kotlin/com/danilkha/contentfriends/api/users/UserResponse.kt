package com.danilkha.contentfriends.api.users

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserResponse (
    val id: String,
    val fullName: String,
    val avatarUrl: String?,
    val login: String,
    val email: String,
    val phone: String,
    val role: RoleApiModel,
    val isBlocked: Boolean,
)