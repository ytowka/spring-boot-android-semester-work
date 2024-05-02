package com.danilkha.contentfriendsbackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(
    username: String
) : Exception("user with login $username not found"){
}