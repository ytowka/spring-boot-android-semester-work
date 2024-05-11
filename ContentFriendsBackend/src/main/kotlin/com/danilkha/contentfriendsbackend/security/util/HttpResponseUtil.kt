package com.danilkha.contentfriendsbackend.security.util

import com.danilkha.contentfriends.ExceptionResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToStream
import org.springframework.http.MediaType
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object HttpResponseUtil {

    @OptIn(ExperimentalSerializationApi::class)
    fun putExceptionInResponse(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: Exception,
        exceptionStatus: Int
    ) {
        response.apply {
            status = exceptionStatus
            contentType = MediaType.APPLICATION_JSON_VALUE
        }

        val exceptionResponse = ExceptionResponse(
            status = exceptionStatus,
            error = "Unauthorized",
            message = exception.message,
        )

        Json.encodeToStream(exceptionResponse, response.outputStream);
    }
}