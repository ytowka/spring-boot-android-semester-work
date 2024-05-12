package com.danilkha.conentfrientdsclient.features.review.domain.dto

data class ReviewRequestDto(
    val contentId: Long,
    val mark: Int,
    val text: String
)