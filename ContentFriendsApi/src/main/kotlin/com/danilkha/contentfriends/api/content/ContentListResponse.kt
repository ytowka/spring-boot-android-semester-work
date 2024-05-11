package com.danilkha.contentfriends.api.content

import kotlinx.serialization.Serializable

@Serializable
data class ContentListResponse(
    val content: List<ContentResponse>,
    val page: Int,
    val hasNextPage: Boolean,
)