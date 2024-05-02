package com.danilkha.conentfrientdsclient.features.users.ui

import com.danilkha.conentfrientdsclient.features.users.domain.dto.RoleDto

enum class UserRoleModel {
    USER,
    ADMIN
}

fun RoleDto.toModel(): UserRoleModel = when (this) {
    RoleDto.ADMIN -> UserRoleModel.ADMIN
    RoleDto.USER -> UserRoleModel.USER
}

fun UserRoleModel.toDto(): RoleDto = when (this) {
    UserRoleModel.ADMIN -> RoleDto.ADMIN
    UserRoleModel.USER -> RoleDto.USER
}