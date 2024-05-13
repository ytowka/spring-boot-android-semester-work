package com.danilkha.conentfrientdsclient.core.network

import android.util.Log
import com.danilkha.conentfrientdsclient.features.auth.domain.usecase.GetValidAccessTokenUseCase
import com.danilkha.conentfrientdsclient.features.auth.domain.usecase.LogoutUseCase
import com.danilkha.contentfriends.api.auth.SecurityConsts
import kotlinx.coroutines.runBlocking
import okhttp3.*
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single


@Single
class AuthenticationInterceptor(
    private val getValidAccessTokenUseCase: GetValidAccessTokenUseCase,
    private val logoutUseCase: LogoutUseCase
) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response = runBlocking{
        val accessToken = getValidAccessTokenUseCase()


        val newRequest = accessToken?.let {
            newRequestWithAccessToken(chain.request(), accessToken)
        }
        chain.proceed(newRequest ?: chain.request()).also {
            if(it.code == 401){
                logoutUseCase()
            }
        }
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .header(SecurityConsts.AUTHORIZATION, "${SecurityConsts.BEARER} $accessToken")
            .build()
    }
}