package com.danilkha.conentfrientdsclient.features.users.ui

import androidx.compose.runtime.Immutable
import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import java.util.*

@Immutable
data class UserModel(
    val id: UUID,
    val fullName: String,
    val avatarUrl: String?,
    val login: String,
    val email: String,
    val phone: String,
    val role: UserRoleModel,
    val isBlocked: Boolean,
)

fun UserDto.toUserModel(): UserModel = UserModel(
    id = id,
    fullName = fullName,
    avatarUrl = avatarUrl,
    login = login,
    email = email,
    phone = phone,
    role = role.toModel(),
    isBlocked = isBlocked,
)
