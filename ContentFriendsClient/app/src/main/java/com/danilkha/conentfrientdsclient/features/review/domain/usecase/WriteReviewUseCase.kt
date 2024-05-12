package com.danilkha.conentfrientdsclient.features.review.domain.usecase

import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewRequestDto
import com.danilkha.conentfrientdsclient.features.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory

@Factory
class WriteReviewUseCase(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(reviewRequest: ReviewRequestDto): Result<Unit>{
        return kotlin.runCatching {
            reviewRepository.writeReview(reviewRequest)
        }
    }
}