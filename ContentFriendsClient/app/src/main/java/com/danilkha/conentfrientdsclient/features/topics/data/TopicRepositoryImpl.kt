package com.danilkha.conentfrientdsclient.features.topics.data

import com.danilkha.conentfrientdsclient.core.network.NetworkModule
import com.danilkha.conentfrientdsclient.features.topics.domain.dto.TopicDto
import com.danilkha.conentfrientdsclient.features.topics.domain.repository.TopicRepository
import com.danilkha.contentfriends.api.theme.ThemeApi
import com.danilkha.contentfriends.api.theme.ThemeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single

@Single(binds = [TopicRepository::class])
class TopicRepositoryImpl(
    private val themeApi: ThemeApi
) : TopicRepository {
    override suspend fun getTopics(): List<TopicDto> {
        return withContext(Dispatchers.IO){
            themeApi.getAllThemes().map(ThemeResponse::toDto)
        }
    }
}

private fun ThemeResponse.toDto(): TopicDto = TopicDto(
    id = id,
    name = name,
    contentCount = contentCount,
    imageUrl = NetworkModule.baseUrl + imageUrl,
)