package com.danilkha.conentfrientdsclient.features.users.domain.usecase

import android.util.Log
import com.danilkha.conentfrientdsclient.core.domain.UseCase
import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.GetUserListUseCase.PageResult
import org.koin.core.annotation.Factory

@Factory
class GetUserListUseCase(
    private val userListRepository: UserListRepository
) : UseCase<Int, PageResult>(){

    override suspend fun execute(page: Int): PageResult {
        val data = userListRepository.getUserList(page)
        return PageResult(
            users = data.first,
            page = page,
            hasNextPage = data.second
        )
    }


    class PageResult(
        val users: List<UserDto>,
        val page: Int,
        val hasNextPage: Boolean,
    )
}