package com.danilkha.contentfriends.api.users

interface UserApi {

    fun getAll(page: Int): UserListResponse
    fun search(query: String): List<UserResponse>
    fun get(id: String): UserResponse
    fun update(user: UserRequest)
    fun getMe() : UserResponse
}