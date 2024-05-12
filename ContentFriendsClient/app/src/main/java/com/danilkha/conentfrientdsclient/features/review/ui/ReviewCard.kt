package com.danilkha.conentfrientdsclient.features.review.ui

import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewDto

data class ReviewCard(
    val reviewModel: ReviewModel,
    val reviewUserInfo: ReviewUserInfo,
)

fun ReviewDto.toReviewModel(): ReviewCard = ReviewCard(
    reviewModel = ReviewModel(
        contentId = contentId,
        userName = userName,
        mark = mark,
        writeTime = writeTime,
        text = text
    ),
    reviewUserInfo = ReviewUserInfo(
        userId = userId,
        userAvatarUrl = userAvatarUrl,
        userName = userName
    )
)
