package com.danilkha.conentfrientdsclient.features.content.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class SearchContentUseCase(
    private val contentRepository: ContentRepository
) : UseCase<SearchContentUseCase.Params, List<ContentDto>>(){

    override suspend fun execute(params: Params): List<ContentDto> {
        return contentRepository.searchContent(params.topicId, params.query)
    }

    class Params(
        val topicId: Long,
        val query: String
    )
}