package com.danilkha.conentfrientdsclient.features.auth.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.SimpleFlowUseCase
import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class IsLoggedInUseCase(
    val authRepository: AuthRepository
): SimpleFlowUseCase<Boolean>() {

    override fun execute(): Flow<Boolean> {
        return authRepository.getAccessToken().map {
            it != null
        }
    }
}