package com.danilkha.conentfrientdsclient.features.review.ui.list

import com.danilkha.conentfrientdsclient.core.ui.PagingState
import com.danilkha.conentfrientdsclient.features.review.ui.ReviewCard

data class ReviewListState(
    val contentId: Long = 0,
    val listState: PagingState<ReviewCard> = PagingState.initial(),
)
