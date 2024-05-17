package com.danilkha.conentfrientdsclient.features.auth.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.auth.domain.dto.RegisterRequestDto
import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AuthRepository
import org.koin.core.annotation.Factory

@Factory
class RegisterUseCase(
    val authRepository: AuthRepository
): UseCase<RegisterRequestDto, Unit>() {

    override suspend fun execute(params: RegisterRequestDto) {
        authRepository.createAccount(params)
    }


}