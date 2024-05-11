package com.danilkha.conentfrientdsclient.features.content.domain.usecase

import com.danilkha.conentfrientdsclient.features.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetAllContentUseCase(
    private val contentRepository: ContentRepository,
) {

    suspend operator fun invoke(
        themeId: Long, page: Int
    ) = kotlin.runCatching {
        contentRepository.getAllContent(themeId, page)
    }
}