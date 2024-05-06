package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.review.ReviewListResponse
import com.danilkha.contentfriends.api.review.ReviewRequest
import com.danilkha.contentfriends.api.review.ReviewResponse
import java.util.*

interface ReviewService {
    fun getReviewsByContent(contentId: Long, page: Int): ReviewListResponse
    fun getReviewsByUser(userId: UUID, page: Int): ReviewListResponse
    fun writeReview(reviewRequest: ReviewRequest)
    fun editReview(reviewRequest: ReviewRequest)
    fun deleteReview(reviewId: Long)
}