package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.review.ReviewRequest
import com.danilkha.contentfriendsbackend.model.Role
import com.danilkha.contentfriendsbackend.security.userdetails.AccountUserDetails
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewServiceTest {


    @Autowired
    lateinit var reviewService: ReviewService

    @BeforeEach
    fun setUp() {
        val authorities: List<SimpleGrantedAuthority> = (Role.ADMIN.authorities + Role.ADMIN)
            .map { authority ->
                SimpleGrantedAuthority(authority.name)
            }

        val user: UserDetails = AccountUserDetails(
            id = UUID.fromString("94625995-541d-4455-a2bb-7ddab33e80d7"),
            authorities = authorities.toMutableList(),
            password = "admin2222",
            username = "test_user",
            isCredentialsNonExpired = true,
            isAccountNonLocked = true,
            isEnabled = true,
            isAccountNonExpired = true,
        )

        SecurityContextHolder.getContext().authentication = TestingAuthenticationToken(
            user, Object()
        )
    }


    @Test
    fun testSecurityContext(){
        val user = SecurityContextHolder.getContext().authentication.principal as AccountUserDetails
        assertEquals("test_user", user.username)
    }

    @Test
    fun `user write review and it saves`(){
        val user = SecurityContextHolder.getContext().authentication.principal as AccountUserDetails
        val userId = user.id
        val reviewRequest = ReviewRequest(
            contentId = 1,
            mark = 5,
            text = "test_"+ UUID.randomUUID().toString()
        )
        reviewService.writeReview(reviewRequest)

        val review = reviewService.getReviewsByUser(userId, 0).reviews.first {
            it.mark == reviewRequest.mark
                    && it.text == reviewRequest.text
                    && it.contentId == reviewRequest.contentId
        }
        reviewService.deleteReview(reviewId = review.id)
        assert(true)
    }

    @Test
    fun `get review by topic`(){
        val reviewRequest = ReviewRequest(
            contentId = 1,
            mark = 5,
            text = "test_"+ UUID.randomUUID().toString()
        )
        reviewService.writeReview(reviewRequest)

        val review = reviewService.getReviewsByContent(1, 0).reviews.first {
            it.mark == reviewRequest.mark
                    && it.text == reviewRequest.text
                    && it.contentId == reviewRequest.contentId
        }
        reviewService.deleteReview(reviewId = review.id)
        assert(true)
    }

    @Test
    fun `get review by user and content`(){
        val user = SecurityContextHolder.getContext().authentication.principal as AccountUserDetails
        val userId = user.id
        val reviewRequest = ReviewRequest(
            contentId = 1,
            mark = 5,
            text = "test_"+ UUID.randomUUID().toString()
        )
        reviewService.writeReview(reviewRequest)

        val review = reviewService.getReviewByUserContent(userId, 1)

        reviewService.deleteReview(reviewId = review.id)
        assert(true)
    }

    @Test
    fun `user write review and edits review`(){
        val user = SecurityContextHolder.getContext().authentication.principal as AccountUserDetails
        val userId = user.id
        var reviewRequest = ReviewRequest(
            contentId = 1,
            mark = 5,
            text = "test_"+ UUID.randomUUID().toString()
        )
        reviewService.writeReview(reviewRequest)

        var review = reviewService.getReviewsByUser(userId, 0).reviews.firstOrNull {
            it.mark == reviewRequest.mark
                    && it.text == reviewRequest.text
                    && it.contentId == reviewRequest.contentId
        }

        assert(review != null)


        reviewRequest = ReviewRequest(
            contentId = 1,
            mark = 10,
            text = "test_"+ UUID.randomUUID().toString(),
        )

        reviewService.editReview(reviewRequest)


        review = reviewService.getReviewsByUser(userId, 0).reviews.firstOrNull() {
            it.mark == reviewRequest.mark
                    && it.text == reviewRequest.text
                    && it.contentId == reviewRequest.contentId
        }
        val reviewExists = review != null


        reviewService.deleteReview(reviewId = review!!.id)
        assert(reviewExists)
    }
}