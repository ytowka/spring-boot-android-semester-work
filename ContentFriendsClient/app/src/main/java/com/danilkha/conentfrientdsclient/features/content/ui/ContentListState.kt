package com.danilkha.conentfrientdsclient.features.content.ui

import com.danilkha.conentfrientdsclient.core.ui.PagingState

data class ContentListState(
    val pagerState: PagingState<ContentModel> = PagingState.initial(),
    val recommendedContent: List<ContentModel> = listOf(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val searchList: List<ContentModel> = listOf(),
    val topicName: String,
){

    val currentPagerState = if(searchQuery.isEmpty()) {
        pagerState
    } else {
        PagingState.static(searchList)
    }
}
