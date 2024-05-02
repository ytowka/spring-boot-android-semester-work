package com.danilkha.conentfrientdsclient.features.users.data.remote

import com.danilkha.contentfriends.api.users.UserApi
import com.danilkha.contentfriends.api.users.UserListResponse
import com.danilkha.contentfriends.api.users.UserRequest
import com.danilkha.contentfriends.api.users.UserResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory

@Factory(binds = [UserApi::class])
class UserApiImpl(
    private val httpClient: HttpClient,
) : UserApi{

    override fun getAll(page: Int): UserListResponse = runBlocking{
        httpClient.get("/api/users"){
            parameter("page", page)
        }.body()
    }

    override fun search(query: String): List<UserResponse> = runBlocking{
        httpClient.get("/api/users"){
            parameter("q", query)
        }.body()
    }

    override fun get(id: String): UserResponse = runBlocking{
        httpClient.get("/api/users/$id").body()
    }

    override fun update(user: UserRequest) = runBlocking{
        httpClient.put("/api/users") {
            setBody(user)
        }
        Unit
    }

    override fun getMe(): UserResponse = runBlocking{
        httpClient.get("/api/users/me").body()
    }
}