package com.danilkha.contentfriendsbackend.exception

import org.springframework.http.HttpStatus

open class ServiceException(
    val httpStatus: HttpStatus,
    override val message: String?
): Exception(message){
}