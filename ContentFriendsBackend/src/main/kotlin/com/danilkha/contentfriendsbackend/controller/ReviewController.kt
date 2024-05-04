package com.danilkha.contentfriendsbackend.controller

import com.danilkha.contentfriends.api.content.ContentListResponse
import com.danilkha.contentfriends.api.review.ReviewApi
import com.danilkha.contentfriends.api.review.ReviewRequest
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ReviewController : ReviewApi{

    override fun getReviewsByContent(contentId: Long, page: Int): ContentListResponse {
        TODO("Not yet implemented")
    }

    override fun getReviewsByUser(userId: UUID, page: Int): ContentListResponse {
        TODO("Not yet implemented")
    }

    override fun writeReview(reviewRequest: ReviewRequest) {
        TODO("Not yet implemented")
    }

    override fun editReview(reviewRequest: ReviewRequest) {
        TODO("Not yet implemented")
    }

    override fun deleteReview(reviewId: Long) {
        TODO("Not yet implemented")
    }

}