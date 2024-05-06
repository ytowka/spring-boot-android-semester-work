package com.danilkha.contentfriendsbackend.controller

import com.danilkha.contentfriends.api.content.ContentListResponse
import com.danilkha.contentfriends.api.review.ReviewApi
import com.danilkha.contentfriends.api.review.ReviewListResponse
import com.danilkha.contentfriends.api.review.ReviewRequest
import com.danilkha.contentfriendsbackend.service.ReviewService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/reviews")
class ReviewController(
    private val reviewService: ReviewService
) : ReviewApi{

    @GetMapping("/content/{contentId}")
    override fun getReviewsByContent(@PathVariable contentId: Long, @RequestParam page: Int): ReviewListResponse {
        return reviewService.getReviewsByContent(contentId, page)
    }

    @GetMapping("/user/{userId}")
    override fun getReviewsByUser(@PathVariable userId: UUID, @RequestParam page: Int): ReviewListResponse {
        return reviewService.getReviewsByUser(userId, page)
    }

    @PostMapping
    override fun writeReview(reviewRequest: ReviewRequest) {
        return reviewService.writeReview(reviewRequest)
    }

    @PutMapping
    override fun editReview(reviewRequest: ReviewRequest) {
        return reviewService.editReview(reviewRequest)
    }

    @DeleteMapping("/{reviewId}")
    override fun deleteReview(@PathVariable reviewId: Long) {
        return reviewService.deleteReview(reviewId)
    }

}