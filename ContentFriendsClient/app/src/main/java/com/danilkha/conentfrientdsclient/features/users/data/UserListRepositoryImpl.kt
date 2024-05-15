package com.danilkha.conentfrientdsclient.features.users.data

import com.danilkha.conentfrientdsclient.core.network.ApiException
import com.danilkha.conentfrientdsclient.core.network.NetworkModule
import com.danilkha.conentfrientdsclient.features.users.domain.dto.RoleDto
import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.dto.toRequest
import com.danilkha.conentfrientdsclient.features.users.domain.repository.UserListRepository
import com.danilkha.contentfriends.api.users.RoleApiModel
import com.danilkha.contentfriends.api.users.UserApi
import com.danilkha.contentfriends.api.users.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory(binds = [UserListRepository::class])
class UserListRepositoryImpl(
    private val userApi: UserApi
) : UserListRepository {

    private var me: UserDto? = null

    override suspend fun getUserList(page: Int): Pair<List<UserDto>, Boolean> {
        return withContext(Dispatchers.IO) {
            val response = userApi.getAll(page)
            return@withContext response.list.map(UserResponse::toDto) to response.hasNextPage
        }
    }

    override suspend fun searchUser(query: String): List<UserDto> {
        return withContext(Dispatchers.IO) {
            userApi.search(query).map(UserResponse::toDto)
        }
    }

    override suspend fun getUserById(id: String): UserDto = withContext(Dispatchers.IO) {
        userApi.get(id).toDto()
    }

    override suspend fun updateUser(userDto: UserDto) = withContext(Dispatchers.IO) {
        if (userDto.id == me?.id) {
            me = userDto
        }
        userApi.update(userDto.toRequest())
    }

    override suspend fun getMe(): UserDto {
        val _me = me
        return withContext (Dispatchers.IO) {
            if (_me == null) {
                val account = userApi.getMe().toDto()
                me = account
                account
            } else {
                _me
            }
        }
    }

    override suspend fun getMatchScore(userId: UUID): Float? {
        return try {
            userApi.getUserTasteMatchScore(userId).score
        } catch (e: ApiException){
            return null
        }
    }
}

fun UserResponse.toDto(): UserDto = UserDto(
    id = UUID.fromString(this.id),
    fullName = this.fullName,
    email = this.email,
    phone = this.phone,
    avatarUrl = NetworkModule.baseUrl + this.avatarUrl,
    login = login,
    role = role.toDto(),
    isBlocked = isBlocked,
)

fun RoleApiModel.toDto(): RoleDto = when (this) {
    RoleApiModel.USER -> RoleDto.USER
    RoleApiModel.ADMIN -> RoleDto.ADMIN
}