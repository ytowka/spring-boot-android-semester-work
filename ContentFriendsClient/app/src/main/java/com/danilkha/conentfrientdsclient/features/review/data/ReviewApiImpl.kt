package com.danilkha.conentfrientdsclient.features.review.data

import com.danilkha.conentfrientdsclient.core.network.bodyOrThrow
import com.danilkha.contentfriends.api.review.ReviewApi
import com.danilkha.contentfriends.api.review.ReviewListResponse
import com.danilkha.contentfriends.api.review.ReviewRequest
import com.danilkha.contentfriends.api.review.ReviewResponse
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import java.util.*

@Factory(binds = [ReviewApi::class])
class ReviewApiImpl(
    private val httpClient: HttpClient
) : ReviewApi{

    override fun getReviewsByContent(contentId: Long, page: Int): ReviewListResponse  = runBlocking{
        httpClient.get("/api/reviews/content/$contentId"){
            parameter("page", page)
        }.bodyOrThrow()
    }

    override fun getReviewsByUser(userId: UUID, page: Int): ReviewListResponse = runBlocking {
        httpClient.get("/api/reviews/user/$userId"){
            parameter("page", page)
        }.bodyOrThrow()
    }

    override fun getReviewByUserContent(userId: UUID, contentId: Long): ReviewResponse = runBlocking {
        httpClient.get("/api/reviews/user/$userId/content/$contentId").bodyOrThrow()
    }

    override fun writeReview(reviewRequest: ReviewRequest): Unit = runBlocking {
        httpClient.post("/api/reviews") {
            setBody(reviewRequest)
        }
    }

    override fun editReview(reviewRequest: ReviewRequest): Unit = runBlocking {
        httpClient.put("/api/reviews") {
            setBody(reviewRequest)
        }
    }

    override fun deleteReview(reviewId: Long): Unit = runBlocking {
        httpClient.delete("/api/reviews/$reviewId")
    }
}