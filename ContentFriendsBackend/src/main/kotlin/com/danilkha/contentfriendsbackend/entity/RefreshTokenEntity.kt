package com.danilkha.contentfriendsbackend.entity

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "refresh_token")
data class RefreshTokenEntity(
    @Id
    val userId: UUID,
    val token: String,
    val expiresIn: Long
)