package com.danilkha.conentfrientdsclient.features.auth.data.remote

import com.danilkha.contentfriends.ExceptionResponse
import com.danilkha.contentfriends.api.auth.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory(binds = [AuthApi::class])
class AuthApiImpl(
    @Named("auth") val httpClient: HttpClient
) : AuthApi{

    override fun register(registerRequest: RegisterRequest): TokenPairResponse = runBlocking{
        httpClient.post("api/auth/sign-up") {
            setBody(registerRequest)
        }.bodyOrThrow()
    }

    override fun login(loginRequest: LoginRequest): TokenPairResponse = runBlocking{
        httpClient.post("api/auth/sign-in") {
            setBody(loginRequest)
        }.bodyOrThrow()
    }

    override  fun refreshToken(refreshTokenRequest: RefreshTokenRequest): TokenPairResponse = runBlocking{
        httpClient.post("api/auth/refresh-token") {
            setBody(refreshTokenRequest)
        }.bodyOrThrow()
    }


    private suspend inline fun <reified T> HttpResponse.bodyOrThrow(): T {
        if(status.isSuccess()){
            return body()
        }else{
            val errorBody: ExceptionResponse = body()
            throw Exception(errorBody.message)
        }
    }
}