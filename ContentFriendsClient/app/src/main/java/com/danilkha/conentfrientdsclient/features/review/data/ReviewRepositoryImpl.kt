package com.danilkha.conentfrientdsclient.features.review.data

import com.danilkha.conentfrientdsclient.core.network.NetworkModule
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewDto
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewListResponseDto
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewRequestDto
import com.danilkha.conentfrientdsclient.features.review.domain.repository.ReviewRepository
import com.danilkha.contentfriends.api.review.ReviewApi
import com.danilkha.contentfriends.api.review.ReviewListResponse
import com.danilkha.contentfriends.api.review.ReviewRequest
import com.danilkha.contentfriends.api.review.ReviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import java.util.*

@Single
class ReviewRepositoryImpl(
    private val reviewApi: ReviewApi,
) : ReviewRepository {


    override suspend fun getReviewsByContent(contentId: Long, page: Int): ReviewListResponseDto {
        return withContext(Dispatchers.IO) {
            reviewApi.getReviewsByContent(contentId, page).toDto()
        }
    }

    override suspend fun getReviewsByUser(userId: UUID, page: Int): ReviewListResponseDto {
        return withContext(Dispatchers.IO){
            reviewApi.getReviewsByUser(userId, page).toDto()
        }
    }

    override suspend fun getReviewsByUserAndContent(userId: UUID, contentId: Long): ReviewDto {
        return withContext(Dispatchers.IO){
            reviewApi.getReviewByUserContent(userId, contentId).toDto()
        }
    }

    override suspend fun writeReview(reviewRequest: ReviewRequestDto) {
        return withContext(Dispatchers.IO) {
            reviewApi.writeReview(reviewRequest.toApiRequest())
        }
    }

    override suspend fun editReview(reviewRequest: ReviewRequestDto) {
        return withContext(Dispatchers.IO) {
            reviewApi.editReview(reviewRequest.toApiRequest())
        }
    }

    override suspend fun deleteReview(reviewId: Long) {
        return withContext(Dispatchers.IO) {
            reviewApi.deleteReview(reviewId)
        }
    }
}

fun ReviewResponse.toDto(): ReviewDto = ReviewDto(
    id = id,
    userId = this.userId,
    contentId = contentId,
    contentName = contentName,
    userAvatarUrl = NetworkModule.baseUrl+userAvatarUrl,
    userName = userName,
    mark = mark,
    writeTime = writeTime,
    text = text
)

fun ReviewListResponse.toDto(): ReviewListResponseDto = ReviewListResponseDto(
    reviews = reviews.map { it.toDto() },
    page = page,
    hasNextPage = hasNextPage,
)

fun ReviewRequestDto.toApiRequest(): ReviewRequest = ReviewRequest(
    contentId = contentId,
    mark = mark,
    text = text,
)