package com.danilkha.contentfriendsbackend.security.config

import com.danilkha.contentfriendsbackend.security.filter.TokenAuthenticationFilter
import com.danilkha.contentfriendsbackend.security.userdetails.TokenAuthenticationUserDetailsService
import com.danilkha.contentfriendsbackend.service.AuthenticationService
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class WebSecurityConfig(
    private val authorizationUserDetailsService: TokenAuthenticationUserDetailsService,
    private val authenticationService: AuthenticationService
) : WebSecurityConfigurerAdapter() {


    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(*IGNORE)
    }

    override fun configure(http: HttpSecurity) {
        http.run {
            sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

            cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()

            authorizeRequests()
                .antMatchers(*PERMIT_ALL).permitAll()
                .anyRequest().authenticated()
                .and()
            authenticationProvider(authenticationProvider())
                .addFilterBefore(tokenAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)

            exceptionHandling()
                .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
        }
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = PreAuthenticatedAuthenticationProvider()
        authenticationProvider.setPreAuthenticatedUserDetailsService(authorizationUserDetailsService)
        return authenticationProvider
    }

    override fun authenticationManager(): AuthenticationManager {
        return ProviderManager(listOf(authenticationProvider()))
    }

    fun tokenAuthorizationFilter(): RequestHeaderAuthenticationFilter {
        return TokenAuthenticationFilter(
            authenticationService, authenticationManager()
        )
    }

    companion object {
        private val PERMIT_ALL = arrayOf(
            "/api/auth/sign-in",
            "/api/auth/sign-up",
            "/api/auth/refresh-token",
            "/files/**"
        )
        private val IGNORE = arrayOf<String>()
    }
}
