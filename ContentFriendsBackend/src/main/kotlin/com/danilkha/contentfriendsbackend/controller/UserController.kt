package com.danilkha.contentfriendsbackend.controller

import com.danilkha.contentfriends.api.users.*
import com.danilkha.contentfriendsbackend.service.TopicService
import com.danilkha.contentfriendsbackend.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val topicService: TopicService
) : UserApi{


    @GetMapping(params = ["page"])
    override fun getAll(@RequestParam page: Int): UserListResponse {
        return userService.getAll(page)
    }

    @GetMapping(params = ["q"])
    override fun search(@RequestParam q: String): List<UserResponse> {
        return userService.search(q)
    }

    @GetMapping("/{id}")
    override fun get(@PathVariable id: String): UserResponse {
        return userService.getById(id)
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    override fun update(@RequestBody user: UserRequest) {
       userService.update(user)
    }

    @GetMapping("/me")
    override fun getMe(): UserResponse {
        return userService.getMe()
    }

    @GetMapping("/match-score/{id}")
    override fun getUserTasteMatchScore(@PathVariable("id") id: UUID): UserTasteMatchScoreResponse {
        return topicService.getMatchScore(id)
    }
}