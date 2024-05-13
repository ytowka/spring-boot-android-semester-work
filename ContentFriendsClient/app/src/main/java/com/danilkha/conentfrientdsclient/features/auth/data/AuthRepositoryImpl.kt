package com.danilkha.conentfrientdsclient.features.auth.data

import android.content.Context
import android.provider.OpenableColumns
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.danilkha.conentfrientdsclient.features.auth.data.remote.AvatarApi
import com.danilkha.conentfrientdsclient.features.auth.domain.dto.LoginRequestDto
import com.danilkha.conentfrientdsclient.features.auth.domain.dto.RegisterRequestDto
import com.danilkha.conentfrientdsclient.features.auth.domain.dto.TokenPairDto
import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AccessTokenDto
import com.danilkha.conentfrientdsclient.features.auth.domain.repository.AuthRepository
import com.danilkha.contentfriends.api.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.core.annotation.Single
import java.util.*


@Single(binds = [AuthRepository::class])
class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val dataStore: DataStore<Preferences>,
    private val avatarApi: AvatarApi,
    context: Context,
) : AuthRepository{

    private var currentToken: AccessTokenDto? = null
    private val contentResolver = context.contentResolver

    override suspend fun createAccount(registerRequestDto: RegisterRequestDto) {
        withContext(Dispatchers.IO){
            val response = authApi.register(
                RegisterRequest(
                    login = registerRequestDto.username,
                    password = registerRequestDto.password,
                    phone = registerRequestDto.phone,
                    email = registerRequestDto.email,
                    fullName = registerRequestDto.fullName,
                )
            )
            if(registerRequestDto.avatarUri != null) {
                val name = contentResolver.query(registerRequestDto.avatarUri, null, null, null, null, null)?.use {
                    val nameIndex: Int = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    it.moveToFirst()
                    it.getString(nameIndex)
                }
                Log.d("debugg", "createAccount() $name")
                if(name != null){
                    contentResolver.openInputStream(registerRequestDto.avatarUri)?.use { it.readBytes() }?.let {
                        avatarApi.loadAvatar(it, name, response.accessToken)
                    }
                }
            }
            saveToken(response)
        }
    }

    override suspend fun login(loginRequestDto: LoginRequestDto) {
        withContext(Dispatchers.IO){
            val response = authApi.login(LoginRequest(loginRequestDto.username, loginRequestDto.password))
            saveToken(response)
        }
    }

    override suspend fun logout() {
        dataStore.edit {
            it.remove(stringPreferencesKey(KEY_REFRESH_TOKEN))
            it.remove(stringPreferencesKey(KEY_ACCESS_TOKEN))
            it.remove(stringPreferencesKey(KEY_ACCESS_TOKEN_EXPIRES))
        }
    }

    override suspend fun getNewToken(refreshToken: String): TokenPairDto {
        return withContext(Dispatchers.IO){
            val tokenResponse = try {
                authApi.refreshToken(RefreshTokenRequest(token = refreshToken))
            }catch (e: Exception){
                dataStore.edit {
                    it.remove(stringPreferencesKey(KEY_REFRESH_TOKEN))
                    it.remove(stringPreferencesKey(KEY_ACCESS_TOKEN))
                    it.remove(stringPreferencesKey(KEY_ACCESS_TOKEN_EXPIRES))
                }
                throw e
            }
            saveToken(tokenResponse)
             TokenPairDto(tokenResponse.accessToken, tokenResponse.refreshToken)
        }

    }

    override fun getAccessToken(): Flow<AccessTokenDto?> = dataStore.data.map{
        val token = it[stringPreferencesKey(KEY_ACCESS_TOKEN)]
        val expires = it[longPreferencesKey(KEY_ACCESS_TOKEN_EXPIRES)]
        if(token != null && expires != null){
            AccessTokenDto(token, expires)
        }else null
    }.distinctUntilChanged()
        .onStart {
            if(currentToken != null) { emit(currentToken) }
    }

    override suspend fun getRefreshToken(): String? {
        return dataStore.data.first()[stringPreferencesKey(KEY_REFRESH_TOKEN)]
    }

    private suspend fun saveToken(tokenPairResponse: TokenPairResponse){
        val tokenExpiration = getTokenExpiration(tokenPairResponse.accessToken)
        currentToken = AccessTokenDto(tokenPairResponse.accessToken, tokenExpiration)
        dataStore.edit {
            it[stringPreferencesKey(KEY_REFRESH_TOKEN)] = tokenPairResponse.refreshToken
            it[longPreferencesKey(KEY_ACCESS_TOKEN_EXPIRES)] = tokenExpiration
            it[stringPreferencesKey(KEY_ACCESS_TOKEN)] = tokenPairResponse.accessToken
        }
    }

    private fun getTokenExpiration(token: String): Long {
        val rawPayload = token.split(".")[1]

        val payloadMap: JsonObject = Json.decodeFromString(
            String(Base64.getDecoder().decode(rawPayload))
        )

        return payloadMap["exp"]?.jsonPrimitive?.content?.toLongOrNull()?.let {
            it*1000
        } ?: throw Exception("invalid token")
    }


    companion object{

        private const val KEY_REFRESH_TOKEN = "key_refresh_token"
        private const val KEY_ACCESS_TOKEN = "key_access_token"
        private const val KEY_ACCESS_TOKEN_EXPIRES = "key_access_token_expires"
    }
}