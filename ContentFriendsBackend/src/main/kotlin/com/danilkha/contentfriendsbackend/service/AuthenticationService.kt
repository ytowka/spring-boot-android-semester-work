package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.auth.LoginRequest
import com.danilkha.contentfriends.api.auth.RefreshTokenRequest
import com.danilkha.contentfriends.api.auth.RegisterRequest
import com.danilkha.contentfriends.api.auth.TokenPairResponse
import com.danilkha.contentfriendsbackend.model.AccountDto
import com.danilkha.contentfriendsbackend.security.model.TokenRequest

interface AuthenticationService {

    fun userInfoByToken(token: TokenRequest): AccountDto

    fun register(request: RegisterRequest): TokenPairResponse

    fun login(request: LoginRequest): TokenPairResponse

    fun refreshToken(request: RefreshTokenRequest): TokenPairResponse
}