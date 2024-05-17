package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class GetMatchScoreUseCase(
    private val userListRepository: UserListRepository
) : UseCase<UUID, Float?>(){

    override suspend fun execute(userId: UUID): Float? {
        return userListRepository.getMatchScore(userId)
    }
}