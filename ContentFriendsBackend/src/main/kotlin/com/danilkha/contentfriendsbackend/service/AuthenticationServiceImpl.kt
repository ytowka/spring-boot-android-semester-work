package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.auth.LoginRequest
import com.danilkha.contentfriends.api.auth.RefreshTokenRequest
import com.danilkha.contentfriends.api.auth.RegisterRequest
import com.danilkha.contentfriends.api.auth.TokenPairResponse
import com.danilkha.contentfriendsbackend.exception.UserNotFoundException
import com.danilkha.contentfriendsbackend.model.AccountDto
import com.danilkha.contentfriendsbackend.model.Role
import com.danilkha.contentfriendsbackend.security.exception.AuthenticationHeaderException
import com.danilkha.contentfriendsbackend.security.model.TokenRequest
import com.danilkha.contentfriends.api.auth.SecurityConsts
import com.danilkha.contentfriendsbackend.entity.RefreshTokenEntity
import com.danilkha.contentfriendsbackend.entity.toDto
import com.danilkha.contentfriendsbackend.entity.toEntity
import com.danilkha.contentfriendsbackend.exception.UserAlreadyRegisteredException
import com.danilkha.contentfriendsbackend.repository.AccountRepository
import com.danilkha.contentfriendsbackend.repository.RefreshTokenRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

@Service
class AuthenticationServiceImpl(
    @Value("\${jwt.expiration.access.mills}") private val expirationAccessInMills: Long = 180_000,
    @Value("\${jwt.expiration.refresh.mills}") private val expirationRefreshInMills: Long = 1_800_000,
    @Value("\${jwt.secret}") private val jwtSecret: String,
    private val accountRepository: AccountRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
): AuthenticationService {
    private val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()


    override fun userInfoByToken(token: TokenRequest): AccountDto {
        val claims: Claims = parseAccessToken(token.token)
        val login: String = claims.subject

        val userEntity = accountRepository.getByLogin(login)


        return userEntity?.toDto() ?: throw UserNotFoundException("User not found")
    }

    override fun register(request: RegisterRequest): TokenPairResponse {

        val isRegistered = accountRepository.findByLoginOrEmail(request.login, request.email) != null
        if (isRegistered) {
            throw UserAlreadyRegisteredException()
        }

        val id = UUID.randomUUID()
        val accountDto = AccountDto(
            id = id,
            fullName = request.fullName,
            login = request.login,
            email = request.email,
            phone = request.phone,
            password = passwordEncoder.encode(request.password),
            role = Role.USER,
            isBlocked = false,
            avatarFileName = null
        )
        accountRepository.save(accountDto.toEntity())

        return generateToken(accountDto.login, id, accountDto.role)
    }

    override fun login(request: LoginRequest): TokenPairResponse {
        val accountEntity = accountRepository.getByLogin(request.login) ?: throw UserNotFoundException(request.login)

        if(passwordEncoder.matches(request.password, accountEntity.password)) {
            return generateToken(accountEntity.login, accountEntity.id, accountEntity.role)
        }

        throw AuthenticationHeaderException("Wrong credentials")
    }

    override fun refreshToken(request: RefreshTokenRequest): TokenPairResponse {
        try {
            val id: UUID = UUID.fromString(request.token.split("/")[0])
            val actualRefreshToken = refreshTokenRepository.findById(id).getOrNull()

            if(actualRefreshToken?.token == request.token){
                val user = accountRepository.findById(id).getOrElse {
                    throw UserNotFoundException(id.toString())
                }
                val newToken = generateToken(user.login, user.id, user.role)
                return newToken
            }else{
                throw Exception("Wrong token")
            }
        }catch (e: Exception) {
            throw AuthenticationHeaderException("Invalid token", e)
        }
    }

    fun generateToken(login: String, id: UUID, role: Role): TokenPairResponse {
        val claims = mutableMapOf(
            SecurityConsts.ROLE to role.name,
        )
        claims[Claims.SUBJECT] = login
        val now = System.currentTimeMillis()
        val jwt = Jwts.builder()
            .claims(claims)
            .issuedAt(Date(now))
            .expiration(Date(now + expirationAccessInMills))
            .signWith(getSecretKey())
            .compact()

        val refresh = "$id/${UUID.randomUUID()}"

        refreshTokenRepository.save(RefreshTokenEntity(
            userId = id,
            token = refresh,
            expiresIn = now + expirationRefreshInMills
        ))

        return TokenPairResponse(
            accessToken = jwt,
            refreshToken = refresh,
        )
    }

    private fun getSecretKey(): SecretKey {
        val base64secret = Base64.getEncoder().encode(jwtSecret.toByteArray())
        return Keys.hmacShaKeyFor(base64secret)
    }

    private fun parseAccessToken(accessToken: String): Claims {
        return Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(accessToken)
            .payload
    }
}