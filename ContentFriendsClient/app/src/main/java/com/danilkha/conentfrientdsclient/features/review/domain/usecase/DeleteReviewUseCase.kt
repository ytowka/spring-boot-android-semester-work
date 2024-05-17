package com.danilkha.conentfrientdsclient.features.review.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory

@Factory
class DeleteReviewUseCase(
    private val reviewRepository: ReviewRepository
) : UseCase<Long, Unit>(){

    override suspend fun execute(params: Long) {
        reviewRepository.deleteReview(params)
    }
}