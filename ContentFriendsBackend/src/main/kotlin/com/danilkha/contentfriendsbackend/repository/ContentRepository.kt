package com.danilkha.contentfriendsbackend.repository

import com.danilkha.contentfriendsbackend.entity.ContentEntity
import com.danilkha.contentfriendsbackend.entity.ContentWithReviewView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ContentRepository : JpaRepository<ContentEntity, Long>{

    @Query("""
select * from content as c
left join (
    select r.content_id, avg(r.mark) avg, count(*) count
    from review as r
    group by r.content_id
) as reviews on reviews.content_id = c.id
where c.theme_id = :topicId
order by count limit :size offset :offset
    """, nativeQuery = true)
    fun getContentWithReviews(topicId: Long, offset: Int, size: Int): List<ContentWithReviewView>


    @Query("""
select * from content as c
left join (
    select r.content_id, avg(r.mark) avg, count(*) count
    from review as r
    group by r.content_id
) as reviews on reviews.content_id = c.id
where c.theme_id = :topicId and c.name like '%'+:query+'%'
    """, nativeQuery = true)
    fun searchContentWithReviews(topicId: Long, query: String): List<ContentWithReviewView>
}
