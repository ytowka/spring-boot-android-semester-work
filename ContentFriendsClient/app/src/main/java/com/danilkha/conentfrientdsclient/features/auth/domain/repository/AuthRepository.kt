package com.danilkha.conentfrientdsclient.features.auth.domain.repository

import com.danilkha.conentfrientdsclient.features.auth.domain.dto.LoginRequestDto
import com.danilkha.conentfrientdsclient.features.auth.domain.dto.RegisterRequestDto
import com.danilkha.conentfrientdsclient.features.auth.domain.dto.TokenPairDto
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun createAccount(registerRequestDto: RegisterRequestDto)

    suspend fun login(loginRequestDto: LoginRequestDto)
    suspend fun logout()

    suspend fun getNewToken(refreshToken: String): TokenPairDto

    fun getAccessToken(): Flow<AccessTokenDto?>
    suspend fun getRefreshToken(): String?

}