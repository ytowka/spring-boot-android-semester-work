package com.danilkha.contentfriendsbackend.repository

import com.danilkha.contentfriendsbackend.entity.RefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, UUID>