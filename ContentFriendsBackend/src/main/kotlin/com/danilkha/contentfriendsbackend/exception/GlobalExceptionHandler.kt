package com.danilkha.contentfriendsbackend.exception

import com.danilkha.contentfriends.ExceptionResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException::class)
    fun handleServiceException(exception: ServiceException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity.status(exception.httpStatus.value())
            .body(ExceptionResponse(
                status = exception.httpStatus.value(),
                error = exception::class.simpleName!!,
                message = exception.message ?: ""
            ))

    }
}