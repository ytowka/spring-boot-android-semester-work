package com.danilkha.conentfrientdsclient.features.review.domain.usecase

import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewListResponseDto
import com.danilkha.conentfrientdsclient.features.review.domain.repository.ReviewRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import java.util.*

@Factory
class GetReviewsByUserUseCase(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(userId: UUID, page: Int): Result<ReviewListResponseDto> = kotlin.runCatching {
        reviewRepository.getReviewsByUser(userId, page)
    }
}