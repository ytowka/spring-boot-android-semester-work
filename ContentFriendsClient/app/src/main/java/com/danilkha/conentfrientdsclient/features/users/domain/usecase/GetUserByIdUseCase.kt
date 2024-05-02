package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class GetUserByIdUseCase(
    private val userListRepository: UserListRepository
) {

    suspend operator fun invoke(id: String): Result<UserDto>{
        return kotlin.runCatching{ userListRepository.getUserById(id) ?: throw RuntimeException("user with id $id not found") }
    }
}