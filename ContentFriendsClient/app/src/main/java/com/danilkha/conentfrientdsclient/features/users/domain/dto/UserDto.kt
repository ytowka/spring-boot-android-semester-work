package com.danilkha.conentfrientdsclient.features.users.domain.dto

import com.danilkha.contentfriends.api.users.RoleApiModel
import com.danilkha.contentfriends.api.users.UserRequest
import java.util.UUID

data class UserDto(
    val id: UUID,
    val fullName: String,
    val avatarUrl: String?,
    val login: String,
    val email: String,
    val phone: String,
    val role: RoleDto,
    val isBlocked: Boolean
)

fun UserDto.toRequest(): UserRequest = UserRequest(
    fullName = fullName,
    login = login,
    email = email,
    phone = phone,
    id = id.toString(),
    role = role.toApiModel(),
    isBlocked = isBlocked,
    password = null,
)

fun RoleDto.toApiModel(): RoleApiModel = when(this){
    RoleDto.USER -> RoleApiModel.USER
    RoleDto.ADMIN -> RoleApiModel.ADMIN
}

