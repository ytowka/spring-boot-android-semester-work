package com.danilkha.conentfrientdsclient.features.content.ui

import androidx.compose.foundation.pager.PagerState
import com.danilkha.conentfrientdsclient.core.ui.PagingState

data class ContentListState(
    val pagerState: PagingState<ContentModel> = PagingState.initial(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val searchList: List<ContentModel> = listOf(),
)
