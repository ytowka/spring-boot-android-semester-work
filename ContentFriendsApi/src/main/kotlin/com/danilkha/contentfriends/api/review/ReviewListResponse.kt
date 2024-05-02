package com.danilkha.contentfriends.api.review

data class ReviewListResponse(
    val reviews: List<ReviewResponse>,
    val page: Int,
    val hasNextPage: Boolean,
)