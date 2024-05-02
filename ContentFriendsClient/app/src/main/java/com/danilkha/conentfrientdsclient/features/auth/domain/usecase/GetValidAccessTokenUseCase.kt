package com.danilkha.conentfrientdsclient.features.auth.domain.usecase

import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class GetValidAccessTokenUseCase(
    val authRepository: AuthRepository
) {

    suspend operator fun invoke(): String? {
        val currentToken = authRepository.getAccessToken().first()
        if (currentToken != null && currentToken.expiresIn > System.currentTimeMillis() + EXPIRATION_THRESHOLD_MILLIS) {
            return currentToken.token
        }
        val refreshToken = authRepository.getRefreshToken() ?: return null

        kotlin.runCatching {

        }
        val newToken = try{
            authRepository.getNewToken(refreshToken)
        }catch(e: Exception){
            null
        }
        return newToken?.accessToken
    }

    companion object{
        const val EXPIRATION_THRESHOLD_MILLIS = 3_000
    }
}