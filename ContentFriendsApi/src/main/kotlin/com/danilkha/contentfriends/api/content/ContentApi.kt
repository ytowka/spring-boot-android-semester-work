package com.danilkha.contentfriends.api.content

interface ContentApi {
    fun getAllContents(themeId: Long, page: Int): ContentListResponse
    fun search(themeId: Long, query: String): List<ContentResponse>
}