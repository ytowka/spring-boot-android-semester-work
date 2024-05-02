package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class GetMeUseCase(
    private val userListRepository: UserListRepository
) {

    suspend operator fun invoke(): Result<UserDto>{
        return kotlin.runCatching {
            userListRepository.getMe()
        }
    }
}