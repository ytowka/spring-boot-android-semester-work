package com.danilkha.contentfriendsbackend.repository

import com.danilkha.contentfriendsbackend.entity.ContentEntity
import com.danilkha.contentfriendsbackend.entity.ContentWithReviewView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ContentRepository : JpaRepository<ContentEntity, Long>{

    @Query("""
select *, theme_id as themeId from content as c
left join (
    select r.content_id, avg(r.mark) avg, count(*) count
    from review as r
    group by r.content_id
) as reviews on reviews.content_id = c.id
where c.theme_id = :topicId
order by count limit :size offset :offset
    """, nativeQuery = true)
    fun getContentWithReviews(
        @Param("topicId") topicId: Long,
        @Param("offset") offset: Int,
        @Param("size") size: Int
    ): List<ContentWithReviewView>


    @Query("""
select *, theme_id as themeId from content as c
left join (
    select r.content_id, avg(r.mark) avg, count(*) count
    from review as r
    group by r.content_id
) as reviews on reviews.content_id = c.id
where c.theme_id = :topicId and lower(c.name) like lower(:query)
    """, nativeQuery = true)
    fun searchContentWithReviews(
        @Param("topicId") topicId: Long,
        @Param("query") query: String): List<ContentWithReviewView>


    @Query("""
select *, theme_id as themeId from content as c
left join (
    select r.content_id, avg(r.mark) avg, count(*) count
    from review as r
    group by r.content_id
) as reviews on reviews.content_id = c.id
where c.id = :contentId
    """, nativeQuery = true)
    fun findByIdWithReviews(
        @Param("contentId") contentId: Long,
    ): ContentWithReviewView?
}

