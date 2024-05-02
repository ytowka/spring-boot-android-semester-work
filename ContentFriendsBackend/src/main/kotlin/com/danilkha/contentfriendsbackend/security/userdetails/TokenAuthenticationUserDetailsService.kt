package com.danilkha.contentfriendsbackend.security.userdetails

import com.danilkha.contentfriendsbackend.model.AccountDto
import com.danilkha.contentfriendsbackend.security.exception.AuthenticationHeaderException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Service


@Service
class TokenAuthenticationUserDetailsService : AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    override fun loadUserDetails(token: PreAuthenticatedAuthenticationToken): UserDetails {
        return loadUserDetails(token.principal as? AccountDto, token.credentials.toString())
    }

    private fun loadUserDetails(accountDto: AccountDto?, token: String): UserDetails {
        return try {
            accountDto?.run {
                val authorities: List<SimpleGrantedAuthority> = (accountDto.role.authorities + accountDto.role)
                    .map { authority ->
                        SimpleGrantedAuthority(authority.name)
                    }
                AccountUserDetails(
                    id = accountDto.id,
                    authorities = authorities.toMutableList(),
                    password = password,
                    username = login,
                    isCredentialsNonExpired = true,
                    isAccountNonLocked = !accountDto.isBlocked,
                    isEnabled = true,
                    isAccountNonExpired = true,
                )
            } ?: throw UsernameNotFoundException(
                "Unknown user by token $token"
            )
        } catch (exception: Exception) {
            throw AuthenticationHeaderException(exception.message)
        }
    }
}