package com.danilkha.conentfrientdsclient.features.content.data

import com.danilkha.conentfrientdsclient.core.network.bodyOrThrow
import com.danilkha.contentfriends.api.content.ContentApi
import com.danilkha.contentfriends.api.content.ContentListResponse
import com.danilkha.contentfriends.api.content.ContentResponse
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory

@Factory(binds = [ContentApi::class],)
class ContentApiImpl(
    private val httpClient: HttpClient
) : ContentApi {
    override fun getAllContents(themeId: Long, page: Int): ContentListResponse = runBlocking{
        httpClient.get("/api/content/"){
            parameter("id", themeId)
            parameter("page", page)
        }.bodyOrThrow()
    }

    override fun search(themeId: Long, query: String): List<ContentResponse> = runBlocking{
        httpClient.get("/api/content"){
            parameter("id", themeId)
            parameter("q", query)
        }.bodyOrThrow()
    }

    override fun getRecommendedContent(): List<ContentResponse> = runBlocking{
        httpClient.get("/api/content/rec").bodyOrThrow()
    }

    override fun getRecommendedContent(topicId: Long): List<ContentResponse> = runBlocking{
        httpClient.get("/api/content/rec/$topicId").bodyOrThrow()
    }
}