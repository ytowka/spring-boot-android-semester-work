package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.content.ContentListResponse
import com.danilkha.contentfriends.api.content.ContentResponse
import com.danilkha.contentfriends.api.theme.ThemeResponse

interface TopicService {

    fun getTopics(): List<ThemeResponse>

    fun getContent(id: Long, page: Int): ContentListResponse

    fun searchContent(id: Long, query: String): List<ContentResponse>
}