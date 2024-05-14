package com.danilkha.contentfriendsbackend.exception

import com.danilkha.contentfriends.ExceptionResponse
import org.slf4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.util.NestedServletException

@RestControllerAdvice
class GlobalExceptionHandler(
    val logger: Logger
) {

    @ExceptionHandler(ServiceException::class)
    fun handleServiceException(exception: ServiceException): ResponseEntity<ExceptionResponse> {
        logger.error("${exception.httpStatus} code with ${exception.message}")
        exception.printStackTrace()
        return ResponseEntity.status(exception.httpStatus.value())
            .body(ExceptionResponse(
                status = exception.httpStatus.value(),
                error = exception::class.simpleName!!,
                message = exception.message ?: ""
            ))
    }

    @ExceptionHandler(Exception::class)
    fun handleServiceException(exception: Exception): ResponseEntity<ExceptionResponse> {
        logger.error("${400} code with ${exception.message}")
        exception.printStackTrace()
        return ResponseEntity.status(400)
            .body(ExceptionResponse(
                status = 400,
                error = exception::class.simpleName!!,
                message = exception.message ?: ""
            ))
    }
}