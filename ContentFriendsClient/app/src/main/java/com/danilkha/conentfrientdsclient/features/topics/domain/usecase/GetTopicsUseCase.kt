package com.danilkha.conentfrientdsclient.features.topics.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.SimpleUseCase
import com.danilkha.conentfrientdsclient.features.topics.domain.dto.TopicDto
import com.danilkha.conentfrientdsclient.features.topics.domain.repository.TopicRepository
import org.koin.core.annotation.Factory

@Factory
class GetTopicsUseCase(
    private val topicRepository: TopicRepository
) : SimpleUseCase<List<TopicDto>>(){

    override suspend fun execute(): List<TopicDto> {
        return topicRepository.getTopics()
    }
}