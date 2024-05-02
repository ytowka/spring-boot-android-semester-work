package com.danilkha.conentfrientdsclient.features.topics.domain.repository

import com.danilkha.conentfrientdsclient.features.topics.domain.dto.TopicDto

interface TopicRepository {
    suspend fun getTopics(): List<TopicDto>
}