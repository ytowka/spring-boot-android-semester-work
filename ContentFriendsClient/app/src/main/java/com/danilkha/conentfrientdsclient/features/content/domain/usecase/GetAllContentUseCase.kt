package com.danilkha.conentfrientdsclient.features.content.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentListResponseDto
import com.danilkha.conentfrientdsclient.features.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetAllContentUseCase(
    private val contentRepository: ContentRepository,
) : UseCase<GetAllContentUseCase.Params, ContentListResponseDto>(){

    override suspend fun execute(params: Params): ContentListResponseDto {
        return contentRepository.getAllContent(params.themeId, params.page)
    }

    data class Params(
        val themeId: Long,
        val page: Int
    )
}