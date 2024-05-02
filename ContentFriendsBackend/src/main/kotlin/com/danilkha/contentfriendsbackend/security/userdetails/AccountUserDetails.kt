package com.danilkha.contentfriendsbackend.security.userdetails

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class AccountUserDetails(
    val id: UUID,
    private val authorities: MutableCollection<GrantedAuthority>,
    private val password: String,
    private val username: String,
    private val isAccountNonExpired: Boolean,
    private val isAccountNonLocked: Boolean,
    private val isCredentialsNonExpired: Boolean,
    private val isEnabled: Boolean,
) : UserDetails {


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities;
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = isAccountNonExpired

    override fun isAccountNonLocked(): Boolean = isAccountNonLocked

    override fun isCredentialsNonExpired(): Boolean = isCredentialsNonExpired

    override fun isEnabled(): Boolean = isEnabled

}