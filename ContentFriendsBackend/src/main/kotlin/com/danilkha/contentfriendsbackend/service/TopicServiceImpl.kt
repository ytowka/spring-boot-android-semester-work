package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.theme.ThemeResponse
import com.danilkha.contentfriendsbackend.entity.toResponse
import com.danilkha.contentfriendsbackend.repository.TopicRepository
import org.springframework.stereotype.Service

@Service
class TopicServiceImpl(
    private val topicRepository: TopicRepository
) : TopicService {
    override fun getTopics(): List<ThemeResponse> {
        return topicRepository.findAll().map { it.toResponse() }
    }
}