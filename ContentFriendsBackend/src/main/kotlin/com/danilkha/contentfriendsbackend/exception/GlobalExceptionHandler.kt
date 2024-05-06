package com.danilkha.contentfriendsbackend.exception

import com.danilkha.contentfriends.api.ExceptionModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException::class)
    fun handleServiceException(exception: ServiceException): ResponseEntity<ExceptionModel> {
        return ResponseEntity.status(exception.httpStatus.value())
            .body(ExceptionModel(
                status = exception.httpStatus.value(),
                name = exception::class.simpleName!!,
                message = exception.message ?: ""
            ))

    }
}