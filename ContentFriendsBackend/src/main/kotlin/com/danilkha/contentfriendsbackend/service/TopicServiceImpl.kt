package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.content.ContentListResponse
import com.danilkha.contentfriends.api.content.ContentResponse
import com.danilkha.contentfriends.api.theme.ThemeResponse
import com.danilkha.contentfriendsbackend.entity.toResponse
import com.danilkha.contentfriendsbackend.repository.ContentRepository
import com.danilkha.contentfriendsbackend.repository.ReviewRepository
import com.danilkha.contentfriendsbackend.repository.TopicRepository
import com.danilkha.contentfriendsbackend.service.UserServiceImpl.Companion
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicServiceImpl(
    private val topicRepository: TopicRepository,
    private val contentRepository: ContentRepository,
    private val reviewRepository: ReviewRepository,
) : TopicService {
    override fun getTopics(): List<ThemeResponse> {
        return topicRepository.findAll().map { it.toResponse() }
    }

    override fun getContent(id: Long, page: Int): ContentListResponse {
        val pageable: Pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE)
        val pageResponse = contentRepository.findAll(pageable)
        /*return ContentListResponse(
            content = pageResponse
        )*/
        TODO()
    }

    override fun searchContent(id: Long, query: String): List<ContentResponse> {
        TODO("Not yet implemented")
    }


    companion object{
        const val DEFAULT_PAGE_SIZE = 10
    }
}