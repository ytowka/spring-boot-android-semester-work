package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class UpdateUserUseCase(
    private val userListRepository: UserListRepository
) : UseCase<UserDto, Unit>(){

    override suspend fun execute(params: UserDto) {
        userListRepository.updateUser(params)
    }
}