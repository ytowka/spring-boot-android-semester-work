package com.danilkha.contentfriendsbackend.controller

import com.danilkha.contentfriends.api.theme.ThemeApi
import com.danilkha.contentfriends.api.theme.ThemeResponse
import com.danilkha.contentfriendsbackend.service.TopicService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/topics")
class TopicController(
    private val topicService: TopicService
) : ThemeApi{

    @GetMapping
    override fun getAllThemes(): List<ThemeResponse> {
        return topicService.getTopics()
    }
}