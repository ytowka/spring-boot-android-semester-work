package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.theme.ThemeResponse

interface TopicService {

    fun getTopics(): List<ThemeResponse>
}