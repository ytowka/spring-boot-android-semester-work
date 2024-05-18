package com.danilkha.conentfrientdsclient.features.review.domain.dto

data class ReviewDto(
    val id: Long,
    val userId: String,
    val contentId: Long,
    val contentName: String,
    val userAvatarUrl: String?,
    val userName: String,
    val mark: Int,
    val writeTime: Long,
    val text: String
)