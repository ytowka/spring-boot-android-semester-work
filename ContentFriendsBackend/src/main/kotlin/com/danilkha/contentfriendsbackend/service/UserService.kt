package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.auth.LoadFileResult
import com.danilkha.contentfriends.api.users.UserListResponse
import com.danilkha.contentfriends.api.users.UserRequest
import com.danilkha.contentfriends.api.users.UserResponse
import org.springframework.web.multipart.MultipartFile

interface UserService{

    fun getAll(page: Int): UserListResponse
    fun search(query: String): List<UserResponse>
    fun getById(id: String): UserResponse
    fun update(user: UserRequest): UserResponse
    fun updateAvatar(file: MultipartFile?): LoadFileResult
    fun getMe(): UserResponse
}
