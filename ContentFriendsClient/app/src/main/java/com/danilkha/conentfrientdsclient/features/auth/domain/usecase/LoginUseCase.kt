package com.danilkha.conentfrientdsclient.features.auth.domain.usecase

import android.util.Log
import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.auth.domain.dto.LoginRequestDto
import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AuthRepository
import org.koin.core.annotation.Factory

@Factory
class LoginUseCase(
    val authRepository: AuthRepository
): UseCase<LoginRequestDto, Unit>() {

    override suspend fun execute(loginRequestDto: LoginRequestDto) {
        authRepository.login(loginRequestDto)
    }
}