package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import android.util.Log
import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class SearchUserUseCase(
    private val userListRepository: UserListRepository
) : UseCase<String, List<UserDto>>() {

    override suspend fun execute(query: String): List<UserDto>{
        return userListRepository.searchUser(query)
    }
}