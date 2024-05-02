package com.danilkha.contentfriends.api.review

data class ReviewRequest(
    val contentId: Long,
    val mark: Int,
    val text: String
)
