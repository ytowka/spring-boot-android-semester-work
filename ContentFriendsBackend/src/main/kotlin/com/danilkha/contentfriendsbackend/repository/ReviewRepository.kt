package com.danilkha.contentfriendsbackend.repository

import com.danilkha.contentfriendsbackend.entity.ReviewEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ReviewRepository : JpaRepository<ReviewEntity, Long> {

    fun findByContentIdOrderByWriteDateTimeDesc(reviewId: Long, pageRequest: PageRequest): Page<ReviewEntity>

    fun findByUserIdOrderByWriteDateTime(userId: UUID, pageRequest: PageRequest): Page<ReviewEntity>

    fun getReviewsByContentId(contentId: Long): List<ReviewEntity>

    fun getReviewsByUserId(userId: UUID): List<ReviewEntity>

    fun getByUserIdAndContentId(userId: UUID, contentId: Long): ReviewEntity?


    @Query("""
        SELECT distinct(c.theme_id) from review as r
         inner join content as c on r.content_id = c.id
         where r.user_id = :userId
    """, nativeQuery = true)
    fun getUserRatedTopics(userId: UUID) : List<Long>
}