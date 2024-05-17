package com.danilkha.conentfrientdsclient.features.content.domain.repository

import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentListResponseDto
import java.util.UUID

interface ContentRepository {
    suspend fun getAllContent(topicId: Long, page: Int): ContentListResponseDto
    suspend fun getById(contentId: Long): ContentDto
    suspend fun searchContent(topicId: Long, query: String): List<ContentDto>
    suspend fun getRecommendedContent(): List<ContentDto>
    suspend fun getRecommendedContent(topicId: Long): List<ContentDto>
}