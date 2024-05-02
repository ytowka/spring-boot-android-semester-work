package com.danilkha.conentfrientdsclient.features.auth.domain.usecase

import android.util.Log
import com.danilkha.conentfrientdsclient.features.auth.domain.dto.LoginRequestDto
import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AuthRepository
import org.koin.core.annotation.Factory

@Factory
class LoginUseCase(
    val authRepository: AuthRepository
){
    suspend operator fun invoke(loginRequestDto: LoginRequestDto): Result<Unit> = runCatching {
        authRepository.login(loginRequestDto)
    }.onFailure {
        Log.d("usecase", "LoginUseCase() failed $it")
    }
}