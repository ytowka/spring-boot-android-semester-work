package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import android.util.Log
import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class GetUserListUseCase(
    private val userListRepository: UserListRepository
) {

    suspend operator fun invoke(page: Int): Result<PageResult>{
        return kotlin.runCatching {
            val data = userListRepository.getUserList(page)
            PageResult(
                users = data.first,
                page = page,
                hasNextPage = data.second
            )
        }.onSuccess {
            Log.d("debugg", "GetUserListUseCase() onSuccess $it")
        }.onFailure {
            Log.d("debugg", "GetUserListUseCase() onFailure $it")
        }
    }

    class PageResult(
        val users: List<UserDto>,
        val page: Int,
        val hasNextPage: Boolean,
    )
}