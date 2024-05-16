package com.danilkha.conentfrientdsclient.features.review.domain.usecase

import android.util.Log
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewListResponseDto
import com.danilkha.conentfrientdsclient.features.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory

@Factory
class GetReviewsByContentUseCase(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(contentId: Long, page: Int): Result<ReviewListResponseDto> {
        return kotlin.runCatching {
            reviewRepository.getReviewsByContent(contentId, page)
        }.onFailure {
            Log.d("usecase", "GetReviewsByContentUseCase failed ${it.message}")
        }
    }
}