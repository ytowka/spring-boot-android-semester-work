package com.danilkha.contentfriendsbackend

import com.danilkha.contentfriendsbackend.repository.ContentRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest
class ContentFriendsBackendApplicationTests {

    @Autowired
    lateinit var contentRepository: ContentRepository

    @Test
    fun contextLoads() {
        assertDoesNotThrow {
           val result = contentRepository.getContentWithReviews(0,0, 10)
            println("size: ${result.size}")
            result.forEach {
                println("result: ${it.contentEntity}, ${it.avg} ${it.count}")
            }
        }
    }

}
