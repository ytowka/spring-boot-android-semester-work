package com.danilkha.conentfrientdsclient.features.content.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetRecommendedContentUseCase(
    private val contentRepository: ContentRepository
) : UseCase<Long, List<ContentDto>>(){

    override suspend fun execute(params: Long): List<ContentDto> {
        return contentRepository.getRecommendedContent(params)
    }
}