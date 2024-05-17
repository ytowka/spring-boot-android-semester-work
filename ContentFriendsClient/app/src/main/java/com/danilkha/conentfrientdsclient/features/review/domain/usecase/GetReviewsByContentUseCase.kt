package com.danilkha.conentfrientdsclient.features.review.domain.usecase

import android.util.Log
import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewListResponseDto
import com.danilkha.conentfrientdsclient.features.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory

@Factory
class GetReviewsByContentUseCase(
    private val reviewRepository: ReviewRepository
) : UseCase<GetReviewsByContentUseCase.Params, ReviewListResponseDto>() {

    override suspend fun execute(params: Params): ReviewListResponseDto {
        return reviewRepository.getReviewsByContent(params.contentId, params.page)
    }

    class Params(
        val contentId: Long,
        val page: Int
    )
}