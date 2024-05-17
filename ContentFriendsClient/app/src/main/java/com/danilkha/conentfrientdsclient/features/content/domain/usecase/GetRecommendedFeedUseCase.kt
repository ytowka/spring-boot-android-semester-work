package com.danilkha.conentfrientdsclient.features.content.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.SimpleUseCase
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetRecommendedFeedUseCase(
    private val contentRepository: ContentRepository
) : SimpleUseCase<List<ContentDto>>(){

    override suspend fun execute(): List<ContentDto> {
        return contentRepository.getRecommendedContent()
    }
}