package com.danilkha.conentfrientdsclient.features.content.domain.usecase

import com.danilkha.conentfrientdsclient.features.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetRecommendedFeedUseCase(
    private val contentRepository: ContentRepository
) {

    suspend operator fun invoke() = kotlin.runCatching {
        contentRepository.getRecommendedContent()
    }
}