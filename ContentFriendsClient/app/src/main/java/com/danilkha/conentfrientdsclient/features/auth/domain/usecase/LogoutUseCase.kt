package com.danilkha.conentfrientdsclient.features.auth.domain.usecase

import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AuthRepository
import org.koin.core.annotation.Factory

@Factory
class LogoutUseCase(
    val authRepository: AuthRepository
) {

    suspend operator fun invoke() {
        authRepository.logout()
    }
}