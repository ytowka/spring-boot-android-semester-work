package com.danilkha.contentfriendsbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.domain.Page

@SpringBootApplication
class ContentFriendsBackendApplication

fun main(args: Array<String>) {
    runApplication<ContentFriendsBackendApplication>(*args)
}
