package com.danilkha.conentfrientdsclient.features.content.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetContentByIdUseCase(
    private val contentRepository: ContentRepository
) : UseCase<Long, ContentDto>(){
    override suspend fun execute(params: Long): ContentDto {
        return contentRepository.getById(params)
    }
}