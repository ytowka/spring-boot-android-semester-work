package com.danilkha.conentfrientdsclient.features.content.domain.dto

import com.danilkha.contentfriends.api.content.ContentResponse

data class ContentListResponseDto(
    val content: List<ContentDto>,
    val page: Int,
    val hasNextPage: Boolean,
)