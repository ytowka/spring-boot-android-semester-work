package com.danilkha.conentfrientdsclient.features.content.domain.usecase

import com.danilkha.conentfrientdsclient.features.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetRecommendedContentUseCase(
    private val contentRepository: ContentRepository
) {

    suspend operator fun invoke(topicId: Long) = kotlin.runCatching {
        contentRepository.getRecommendedContent(topicId)
    }
}