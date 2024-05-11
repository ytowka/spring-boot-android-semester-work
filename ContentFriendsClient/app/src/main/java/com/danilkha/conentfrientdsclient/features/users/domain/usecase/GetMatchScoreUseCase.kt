package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class GetMatchScoreUseCase(
    private val userListRepository: UserListRepository
){

    suspend operator fun invoke(userId: UUID): Result<Float?>{
        return kotlin.runCatching {
            userListRepository.getMatchScore(userId)
        }
    }
}