package com.danilkha.contentfriendsbackend.controller

import com.danilkha.contentfriends.api.auth.*
import com.danilkha.contentfriendsbackend.service.AuthenticationService
import com.danilkha.contentfriendsbackend.service.StorageService
import com.danilkha.contentfriendsbackend.service.UserService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
) : AuthApi{

    @PostMapping("/sign-in")
    override fun login(@RequestBody loginRequest: LoginRequest): TokenPairResponse {
        return authenticationService.login(loginRequest)
    }

    @PostMapping("/refresh-token")
    override fun refreshToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): TokenPairResponse {
        return authenticationService.refreshToken(refreshTokenRequest)
    }

    @PostMapping("/sign-up")
    override fun register(@RequestBody registerRequest: RegisterRequest): TokenPairResponse {
        return authenticationService.register(registerRequest)
    }

    @PostMapping("/sign-up/avatar")
    fun uploadAvatar(@RequestParam("image") file: MultipartFile): LoadFileResult{
        return userService.updateAvatar(file)
    }

}