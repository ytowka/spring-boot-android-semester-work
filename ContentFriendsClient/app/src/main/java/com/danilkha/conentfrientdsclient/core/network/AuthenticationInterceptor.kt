package com.danilkha.conentfrientdsclient.core.network

import com.danilkha.conentfrientdsclient.features.auth.domain.usecase.AuthTokenProvider
import com.danilkha.conentfrientdsclient.features.auth.domain.usecase.LogoutUseCase
import com.danilkha.contentfriends.api.auth.SecurityConsts
import kotlinx.coroutines.runBlocking
import okhttp3.*
import org.koin.core.annotation.Single


@Single
class AuthenticationInterceptor(
    private val authTokenProvider: AuthTokenProvider,
    private val logoutUseCase: LogoutUseCase
) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response = runBlocking{
        val accessToken = authTokenProvider.getToken()

        var newRequest = accessToken?.let {
            newRequestWithAccessToken(chain.request(), accessToken)
        }
        var response = chain.proceed(newRequest ?: chain.request())
        if(response.code == 401){
            val newToken = authTokenProvider.refreshAndGetToken()
            newRequest = newToken?.let {
                newRequestWithAccessToken(chain.request(), it)
            }
            response = chain.proceed(newRequest ?: chain.request())
            if(response.code == 401){
                logoutUseCase()
            }
        }
        response
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .header(SecurityConsts.AUTHORIZATION, "${SecurityConsts.BEARER} $accessToken")
            .build()
    }
}