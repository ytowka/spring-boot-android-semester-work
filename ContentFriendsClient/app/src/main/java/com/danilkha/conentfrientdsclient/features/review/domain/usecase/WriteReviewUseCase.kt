package com.danilkha.conentfrientdsclient.features.review.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewRequestDto
import com.danilkha.conentfrientdsclient.features.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory

@Factory
class WriteReviewUseCase(
    private val reviewRepository: ReviewRepository
) : UseCase<ReviewRequestDto, Unit>() {

    override suspend fun execute(params: ReviewRequestDto) {
        return reviewRepository.writeReview(params)
    }
}