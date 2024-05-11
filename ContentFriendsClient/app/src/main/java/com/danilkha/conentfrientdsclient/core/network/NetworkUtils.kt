package com.danilkha.conentfrientdsclient.core.network

import com.danilkha.contentfriends.ExceptionResponse
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*

object NetworkUtils {

}

suspend inline fun <reified T> HttpResponse.bodyOrThrow(): T {
    if(status.isSuccess()){
        return body()
    }else{
        val errorBody: ExceptionResponse = body()
        throw ApiException(errorBody)
    }
}