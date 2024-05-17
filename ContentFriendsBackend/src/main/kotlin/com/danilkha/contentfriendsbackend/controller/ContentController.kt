package com.danilkha.contentfriendsbackend.controller

import com.danilkha.contentfriends.api.content.ContentApi
import com.danilkha.contentfriends.api.content.ContentListResponse
import com.danilkha.contentfriends.api.content.ContentResponse
import com.danilkha.contentfriendsbackend.service.TopicService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/content")
class ContentController(
    private val topicService: TopicService
) : ContentApi{

    @GetMapping(params = ["id", "page"])
    override fun getAllContents(@RequestParam("id") themeId: Long, @RequestParam("page") page: Int): ContentListResponse {
        return topicService.getContent(themeId, page)
    }

    @GetMapping(params = ["id", "q"])
    override fun search(@RequestParam("id") themeId: Long, @RequestParam("q") query: String): List<ContentResponse> {
        return topicService.searchContent(themeId, query)
    }

    @GetMapping("/{contentId}")
    override fun getById(@PathVariable("contentId") contentId: Long): ContentResponse {
        return topicService.getContentById(contentId)
    }

    @GetMapping("/rec")
    override fun getRecommendedContent(): List<ContentResponse> {
        return topicService.getRecommendedContent()
    }

    @GetMapping("/rec/{topicId}")
    override fun getRecommendedContent(@PathVariable topicId: Long): List<ContentResponse> {
        return topicService.getRecommendedContent(topicId)
    }
}