package com.danilkha.conentfrientdsclient.features.review.domain.dto

data class ReviewDto(
    val userId: String,
    val contentId: Long,
    val userAvatarUrl: String?,
    val userName: String,
    val mark: Int,
    val writeTime: Long,
    val text: String
)