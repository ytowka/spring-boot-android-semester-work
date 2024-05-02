package com.danilkha.conentfrientdsclient.features.auth.domain.usecase

import com.danilkha.conentfrientdsclient.features.auth.domain.dto.RegisterRequestDto
import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AuthRepository
import org.koin.core.annotation.Factory

@Factory
class RegisterUseCase(
    val authRepository: AuthRepository
) {

    suspend operator fun invoke(registerRequestDto: RegisterRequestDto): Result<Unit> = runCatching {
        authRepository.createAccount(registerRequestDto)
    }


}