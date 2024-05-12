package com.danilkha.conentfrientdsclient.features.review.ui

import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewDto

data class ReviewModel(
    val contentId: Long,
    val mark: Int,
    val writeTime: Long,
    val text: String
)

