package com.danilkha.conentfrientdsclient.features.topics.domain.usecase

import com.danilkha.conentfrientdsclient.features.topics.domain.dto.TopicDto
import com.danilkha.conentfrientdsclient.features.topics.domain.repository.TopicRepository
import org.koin.core.annotation.Factory

@Factory
class GetTopicsUseCase(
    private val topicRepository: TopicRepository
) {

    suspend operator fun invoke(): List<TopicDto> {
        return topicRepository.getTopics()
    }
}