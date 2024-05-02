package com.danilkha.contentfriends.api.users

import kotlinx.serialization.Serializable

@Serializable
enum class RoleApiModel {
    ADMIN,
    USER,
}