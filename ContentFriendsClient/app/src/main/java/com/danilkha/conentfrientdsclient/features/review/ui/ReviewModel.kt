package com.danilkha.conentfrientdsclient.features.review.ui

import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewDto

data class ReviewModel(
    val id: Long,
    val contentId: Long,
    val contentName: String,
    val mark: Int,
    val writeTime: Long,
    val text: String
)

