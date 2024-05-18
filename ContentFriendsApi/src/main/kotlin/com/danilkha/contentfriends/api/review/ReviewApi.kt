package com.danilkha.contentfriends.api.review


import java.util.*

interface ReviewApi {

    fun getReviewsByContent(contentId: Long, page: Int): ReviewListResponse
    fun getReviewsByUser(userId: UUID, page: Int): ReviewListResponse
    fun getReviewByUserContent(userId: UUID, contentId: Long) : ReviewResponse
    fun writeReview(reviewRequest: ReviewRequest)
    fun editReview(reviewRequest: ReviewRequest)
    fun deleteReview(reviewId: Long)
}