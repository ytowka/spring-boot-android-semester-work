package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class GetUserByIdUseCase(
    private val userListRepository: UserListRepository
) : UseCase<String, UserDto>(){

    override suspend fun execute(params: String): UserDto {
        return userListRepository.getUserById(params)
    }
}