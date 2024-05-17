package com.danilkha.conentfrientdsclient.features.users.domain.repository

import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import java.util.UUID

interface UserListRepository {

    suspend fun getUserList(page: Int): Pair<List<UserDto>, Boolean>
    suspend fun searchUser(query: String): List<UserDto>
    suspend fun getUserById(id: String): UserDto
    suspend fun updateUser(userDto: UserDto)
    suspend fun getMe(): UserDto
    suspend fun getMatchScore(userId: UUID): Float?
}