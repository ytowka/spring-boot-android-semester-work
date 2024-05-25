package com.danilkha.conentfrientdsclient.features.auth.data.remote

import com.danilkha.conentfrientdsclient.core.network.bodyOrThrow
import com.danilkha.contentfriends.api.auth.LoadFileResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
class AvatarApi(
    @Named("auth") private val httpClient: HttpClient
) {

    suspend fun loadAvatar(file: ByteArray, fileName: String, accessToken: String): String {
        return httpClient.submitFormWithBinaryData(
            url = "api/auth/sign-up/avatar",
            formData = formData {
                append(key = "image", value = file, headers = Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                })
            }
        ){
            header("Authorization", "Bearer $accessToken")
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }
        }.body<LoadFileResult>().imageId
    }

    suspend fun submitNoAvatar(accessToken: String): String {
        return httpClient.post("api/auth/sign-up/avatar/empty"){
            header("Authorization", "Bearer $accessToken")
        }.body<LoadFileResult>().imageId
    }
}