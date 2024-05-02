package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class UpdateUserUseCase(
    private val userListRepository: UserListRepository
) {
    suspend operator fun invoke(userDto: UserDto){
        userListRepository.updateUser(userDto)
    }
}