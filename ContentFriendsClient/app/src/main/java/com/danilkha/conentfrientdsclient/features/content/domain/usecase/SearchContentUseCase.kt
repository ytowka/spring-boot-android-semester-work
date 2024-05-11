package com.danilkha.conentfrientdsclient.features.content.domain.usecase

import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class SearchContentUseCase(
    private val contentRepository: ContentRepository
) {

    suspend operator fun invoke(topicId: Long,  query: String): Result<List<ContentDto>> = kotlin.runCatching {
        contentRepository.searchContent(topicId, query)
    }
}