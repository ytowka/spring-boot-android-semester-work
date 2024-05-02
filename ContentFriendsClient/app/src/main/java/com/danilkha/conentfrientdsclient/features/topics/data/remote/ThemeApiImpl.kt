package com.danilkha.conentfrientdsclient.features.topics.data.remote

import com.danilkha.contentfriends.api.theme.ThemeApi
import com.danilkha.contentfriends.api.theme.ThemeResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory

@Factory(binds = [ThemeApi::class])
class ThemeApiImpl(
    private val httpClient: HttpClient
) : ThemeApi{
    override fun getAllThemes(): List<ThemeResponse> = runBlocking {
        httpClient.get("/api/topics").body()
    }
}