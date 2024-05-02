package com.danilkha.contentfriends.api.review

import com.danilkha.contentfriends.api.content.ContentListResponse
import java.util.UUID

interface ReviewApi {

    fun getReviewsByContent(contentId: Long, page: Int): ContentListResponse
    fun getReviewsByUser(userId: UUID, page: Int): ContentListResponse

    fun writeReview(reviewRequest: ReviewRequest)
    fun editReview(reviewRequest: ReviewRequest)
    fun deleteReview(reviewId: Long)
}