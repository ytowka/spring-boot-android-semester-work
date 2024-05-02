package com.danilkha.contentfriendsbackend.model

import com.danilkha.contentfriends.api.users.RoleApiModel

enum class Role(
    val authorities: Set<Authority>
) {
    USER(setOf(Authority.READ)),
    ADMIN(Authority.all)
}

fun Role.toApiModel(): RoleApiModel = when (this) {
    Role.ADMIN -> RoleApiModel.ADMIN
    Role.USER -> RoleApiModel.USER
}

fun RoleApiModel.toRole(): Role = when (this) {
    RoleApiModel.USER -> Role.USER
    RoleApiModel.ADMIN -> Role.ADMIN
}