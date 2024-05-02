package com.danilkha.conentfrientdsclient.jwt

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Test
import java.util.Base64
import java.util.Date


class JwtParseTest {

    @Test
    fun getExpiration() {
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiVVNFUiIsInN1YiI6ImRhbmlsa2hhIiwiaWF0IjoxNzEzMDgwMTk1LCJleHAiOjE3MTMwODAzNzV9.BYPV-PGM19TI6Ps3KTIRrO5l71f5FKQeXLqWUKmdsCY"

        val expiredAt = getTokenExpiration(token) ?: 0

        println(Date(expiredAt))
    }

    fun getTokenExpiration(token: String): Long? {
        val rawPayload = token.split(".")[1]

        val payloadMap: JsonObject = Json.decodeFromString(
            String(Base64.getDecoder().decode(rawPayload))
        )

        return payloadMap["exp"]?.jsonPrimitive?.content?.toLongOrNull()?.let {
             it*1000
        }
    }
}