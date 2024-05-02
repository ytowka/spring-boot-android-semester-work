package com.danilkha.contentfriendsbackend.security.exception

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AuthenticationHeaderException(msg: String?, causedBy: Exception? = null) : AuthenticationException(msg, causedBy)
