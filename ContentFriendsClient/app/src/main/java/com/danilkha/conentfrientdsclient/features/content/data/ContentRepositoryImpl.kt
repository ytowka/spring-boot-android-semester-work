package com.danilkha.conentfrientdsclient.features.content.data

import com.danilkha.conentfrientdsclient.core.network.NetworkModule
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentListResponseDto
import com.danilkha.conentfrientdsclient.features.content.domain.repository.ContentRepository
import com.danilkha.contentfriends.api.content.ContentApi
import com.danilkha.contentfriends.api.content.ContentListResponse
import com.danilkha.contentfriends.api.content.ContentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(
    binds = [ContentRepository::class],
)
class ContentRepositoryImpl(
    private val contentApi: ContentApi,
) : ContentRepository {
    override suspend fun getAllContent(topicId: Long, page: Int): ContentListResponseDto = withContext(Dispatchers.IO){
        contentApi.getAllContents(topicId, page).toDto()
    }

    override suspend fun getById(contentId: Long): ContentDto = withContext(Dispatchers.IO){
        contentApi.getById(contentId).toDto()
    }

    override suspend fun searchContent(topicId: Long, query: String): List<ContentDto>  = withContext(Dispatchers.IO){
        contentApi.search(topicId, query).map(ContentResponse::toDto)
    }

    override suspend fun getRecommendedContent(): List<ContentDto> = withContext(Dispatchers.IO){
        contentApi.getRecommendedContent().map(ContentResponse::toDto)
    }

    override suspend fun getRecommendedContent(topicId: Long): List<ContentDto> = withContext(Dispatchers.IO){
        contentApi.getRecommendedContent(topicId).map(ContentResponse::toDto)
    }

}

fun ContentResponse.toDto(): ContentDto = ContentDto(
    id = this.id,
    themeId = this.themeId,
    name = this.name,
    imageUrl = NetworkModule.baseUrl+this.imageUrl,
    avgMark = this.avgMark,
    reviewCount = reviewCount,
)

fun ContentListResponse.toDto(): ContentListResponseDto = ContentListResponseDto(
    content = content.map(ContentResponse::toDto),
    page = page,
    hasNextPage = hasNextPage
)