package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.SimpleUseCase
import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class GetMeUseCase(
    private val userListRepository: UserListRepository
) : SimpleUseCase<UserDto>(){

    override suspend fun execute(): UserDto {
        return userListRepository.getMe()
    }
}