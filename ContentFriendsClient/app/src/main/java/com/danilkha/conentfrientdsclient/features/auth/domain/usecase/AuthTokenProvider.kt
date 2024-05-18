package com.danilkha.conentfrientdsclient.features.auth.domain.usecase

import android.util.Log
import com.danilkha.conentfrientdsclient.features.auth.domain.dto.TokenPairDto
import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AccessTokenDto
import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single
import java.util.Date

@Single
class AuthTokenProvider(
    val authRepository: AuthRepository
) {

    val mutex = Mutex()

    @Volatile
    var currentToken: AccessTokenDto? = null

    suspend fun getToken(): String? {
        val accessToken = getAccessToken()
        Log.d("debug", "getToken() called $accessToken")
        if(accessToken != null){
            return accessToken
        }

        mutex.withLock {
            val accessToken = getAccessToken()
            Log.d("debug", "getToken in mutex() called $accessToken")
            if(accessToken != null){
                return accessToken
            }

            val refreshToken = getRefreshToken()
            return refreshToken?.accessToken
        }
    }


    suspend fun refreshAndGetToken() : String? {
        mutex.withLock {
            val refreshToken = getRefreshToken()
            return refreshToken?.accessToken
        }
    }


    private suspend fun getRefreshToken(): TokenPairDto? {
        val refreshToken = authRepository.getRefreshToken().also {
            Log.d("debugg", "getRefreshToken() called = $it")
        } ?: return null


        return try{
            authRepository.getNewToken(refreshToken)
        }catch(e: Exception){
            null
        }
    }


    private fun AccessTokenDto.isFresh() = expiresIn > System.currentTimeMillis() + EXPIRATION_THRESHOLD_MILLIS

    private suspend fun getAccessToken(): String?{
        var token = currentToken?.takeIf { it.isFresh() }
        if(token == null){
            token = authRepository.getAccessToken().first()?.takeIf { it.isFresh() }
            currentToken = token
        }
        return token?.token
    }

    companion object{
        const val EXPIRATION_THRESHOLD_MILLIS = 3_000
    }
}