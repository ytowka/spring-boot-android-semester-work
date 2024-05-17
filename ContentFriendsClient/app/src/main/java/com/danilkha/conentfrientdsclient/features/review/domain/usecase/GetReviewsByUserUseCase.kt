package com.danilkha.conentfrientdsclient.features.review.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewListResponseDto
import com.danilkha.conentfrientdsclient.features.review.domain.repository.ReviewRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import java.util.*

@Factory
class GetReviewsByUserUseCase(
    private val reviewRepository: ReviewRepository
) : UseCase<GetReviewsByUserUseCase.Params, ReviewListResponseDto>() {

    override suspend fun execute(params: Params): ReviewListResponseDto {
       return reviewRepository.getReviewsByUser(params.userId, params.page)
    }

    class Params(
        val userId: UUID,
        val page: Int
    )
}