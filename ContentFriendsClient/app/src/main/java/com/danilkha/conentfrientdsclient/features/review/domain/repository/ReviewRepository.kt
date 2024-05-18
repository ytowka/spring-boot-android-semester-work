package com.danilkha.conentfrientdsclient.features.review.domain.repository

import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewDto
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewListResponseDto
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewRequestDto
import com.danilkha.contentfriends.api.review.ReviewListResponse
import com.danilkha.contentfriends.api.review.ReviewRequest
import java.util.*


interface ReviewRepository {
    suspend fun getReviewsByContent(contentId: Long, page: Int): ReviewListResponseDto
    suspend fun getReviewsByUser(userId: UUID, page: Int): ReviewListResponseDto
    suspend fun getReviewsByUserAndContent(userId: UUID, contentId: Long): ReviewDto
    suspend fun writeReview(reviewRequest: ReviewRequestDto)
    suspend fun editReview(reviewRequest: ReviewRequestDto)
    suspend fun deleteReview(reviewId: Long)
}