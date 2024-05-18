package com.danilkha.conentfrientdsclient.features.review.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewDto
import com.danilkha.conentfrientdsclient.features.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class GetReviewByUserAndContentUseCase(
    private val reviewRepository: ReviewRepository,
) : UseCase<GetReviewByUserAndContentUseCase.Params, ReviewDto>(){


    override suspend fun execute(params: Params): ReviewDto {
        return reviewRepository.getReviewsByUserAndContent(params.userId, params.contentId)
    }


    data class Params(
        val userId: UUID,
        val contentId: Long,
    )
}