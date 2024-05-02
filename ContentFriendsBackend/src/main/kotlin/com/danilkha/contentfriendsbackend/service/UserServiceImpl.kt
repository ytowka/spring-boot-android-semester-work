package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.auth.LoadFileResult
import com.danilkha.contentfriends.api.users.UserListResponse
import com.danilkha.contentfriends.api.users.UserRequest
import com.danilkha.contentfriends.api.users.UserResponse
import com.danilkha.contentfriendsbackend.entity.toDto
import com.danilkha.contentfriendsbackend.exception.UserNotFoundException
import com.danilkha.contentfriendsbackend.model.toResponse
import com.danilkha.contentfriendsbackend.model.toRole
import com.danilkha.contentfriendsbackend.repository.AccountRepository
import com.danilkha.contentfriendsbackend.security.userdetails.AccountUserDetails
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.jvm.optionals.getOrElse

@Service
class UserServiceImpl(
    private val accountRepository: AccountRepository,
    private val storageService: StorageService,
    ) : UserService {

    override fun getAll(page: Int): UserListResponse {
        val pageable: Pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE)
        val pageResponse = accountRepository.findAll(pageable)
        return UserListResponse(
            list = pageResponse.map { it.toDto().toResponse() }.toList(),
            page = page,
            hasNextPage = pageResponse.hasNext()
        )
    }

    override fun search(query: String): List<UserResponse> {
        return accountRepository.queryByLoginOrFullName(query, query).map {
            it.toDto().toResponse()
        }
    }

    override fun getById(id: String): UserResponse {
        return accountRepository.findById(UUID.fromString(id)).getOrElse {
            throw UserNotFoundException("user with id $id not found")
        }.toDto().toResponse()
    }

    override fun update(user: UserRequest): UserResponse {
        val saved = accountRepository.findById(UUID.fromString(user.id)).getOrElse {
            throw UserNotFoundException("user with id ${user.id} not found")
        }
        val updated = saved.copy(
            fullName = user.fullName,
            email = user.email,
            phone = user.phone,
            role = user.role.toRole(),
            isBlocked = user.isBlocked,
        )
        return accountRepository.save(updated).toDto().toResponse()
    }

    override fun updateAvatar(file: MultipartFile): LoadFileResult {
        val fileName = storageService.saveFile(file)
        val user = SecurityContextHolder.getContext().authentication.principal as AccountUserDetails
        accountRepository.setAvatar(fileName, user.id)
        return LoadFileResult(fileName)
    }

    override fun getMe(): UserResponse {
        val userId = SecurityContextHolder.getContext().authentication.principal as AccountUserDetails
        return accountRepository.findById(userId.id).getOrElse {
            throw UserNotFoundException("user with id ${userId.id} not found")
        }.toDto().toResponse()
    }

    companion object{
        const val DEFAULT_PAGE_SIZE = 10
    }
}