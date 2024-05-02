package com.danilkha.contentfriends.api.content

import com.danilkha.contentfriends.api.theme.ThemeResponse
import kotlinx.serialization.Serializable

@Serializable
class ContentListResponse(
    val theme: ThemeResponse,
    val content: List<ContentResponse>,
    val page: Int,
    val hasNextPage: Boolean,
)