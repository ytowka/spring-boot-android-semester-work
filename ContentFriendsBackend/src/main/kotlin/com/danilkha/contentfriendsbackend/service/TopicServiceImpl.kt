package com.danilkha.contentfriendsbackend.service

import com.danilkha.contentfriends.api.content.ContentListResponse
import com.danilkha.contentfriends.api.content.ContentResponse
import com.danilkha.contentfriends.api.theme.ThemeResponse
import com.danilkha.contentfriends.api.users.UserTasteMatchScoreResponse
import com.danilkha.contentfriendsbackend.entity.ContentWithReviewView
import com.danilkha.contentfriendsbackend.entity.toResponse
import com.danilkha.contentfriendsbackend.exception.ServiceException
import com.danilkha.contentfriendsbackend.repository.AccountRepository
import com.danilkha.contentfriendsbackend.repository.ContentRepository
import com.danilkha.contentfriendsbackend.repository.ReviewRepository
import com.danilkha.contentfriendsbackend.repository.TopicRepository
import com.danilkha.contentfriendsbackend.security.userdetails.AccountUserDetails
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.math.abs

@Service
class TopicServiceImpl(
    private val topicRepository: TopicRepository,
    private val contentRepository: ContentRepository,
    private val reviewRepository: ReviewRepository,
) : TopicService {
    override fun getTopics(): List<ThemeResponse> {
        return topicRepository.findAll().map {
            it.toResponse()
        }
    }

    override fun getContent(id: Long, page: Int): ContentListResponse {
        val offset = page* DEFAULT_PAGE_SIZE
        val pageResponse = contentRepository.getContentWithReviews(id, offset, DEFAULT_PAGE_SIZE)
        return ContentListResponse(
            content = pageResponse.map(ContentWithReviewView::toResponse),
            page = page,
            hasNextPage = pageResponse.size >= DEFAULT_PAGE_SIZE,
        )
    }

    override fun searchContent(id: Long, query: String): List<ContentResponse> {
        val pageResponse = contentRepository.searchContentWithReviews(id, "%$query%")
        return pageResponse.map(ContentWithReviewView::toResponse)
    }

    override fun getRecommendedContent(): List<ContentResponse> {
        val user = SecurityContextHolder.getContext().authentication.principal as AccountUserDetails
        val userMarkedThemes = reviewRepository.getUserRatedTopics(user.id)

        val recommendedContent = mutableListOf<ContentResponse>()

        userMarkedThemes.forEach { userMarkedTheme ->
            recommendedContent.addAll(getRecommendedContent(userMarkedTheme).take(3))
        }


        return emptyList()
    }

    override fun getRecommendedContent(topicId: Long): List<ContentResponse> {
        val currentUser = SecurityContextHolder.getContext().authentication.principal as AccountUserDetails

        val reviews = reviewRepository.getReviewsByContentId(topicId)
        val userMarkMap = HashMap<UUID, HashMap<Long, Int>>()

        reviews.forEach { review ->
            val userMarks = userMarkMap[review.userId]
            if(userMarks != null) {
                userMarks[review.contentId] = review.mark
            }else{
                userMarkMap[review.userId] = hashMapOf(review.contentId to review.mark)
            }
        }

        if((userMarkMap[currentUser.id]?.size ?: 0) < USER_MIN_MARK_COUNT_THRESHOLD) {
            throw ServiceException(
                httpStatus = HttpStatus.CONFLICT,
                message = "user don't have enough marks",
            )
        }

        val userScoresList = userMarkMap[currentUser.id]?.let { currentUserMarks ->
             userMarkMap.mapNotNull { (k, marks) ->
                if(k != currentUser.id && marks.size >= USER_MIN_MARK_COUNT_THRESHOLD) {
                    val score = calculateScore(currentUserMarks, marks)
                    UserScore(
                        id = k,
                        score = score.first,
                        markCount = score.second
                    )
                } else null
             }
        }?.filter {
            it.score > SIMILARITY_THRESHOLD
        }?.sortedByDescending {
            it.score * it.markCount
        }

        val recommendedContent = mutableSetOf<Long>()

        val currentUserScores = userMarkMap[currentUser.id]
        userScoresList?.forEach { userScore ->
            val userScores = userMarkMap[userScore.id]
            if(currentUserScores != null && userScores != null) {
                recommendedContent.addAll(getRecommendedElements(currentUserScores, userScores))
            }
        }

        return contentRepository.findAllById(recommendedContent).map {
            it.toResponse(0f, 0)
        }
    }

    override fun getMatchScore(userId: UUID): UserTasteMatchScoreResponse {
        val currentUser = SecurityContextHolder.getContext().authentication.principal as AccountUserDetails

        val user1Marks = reviewRepository.getReviewsByUserId(currentUser.id).associate{
            it.contentId to it.mark
        }

        val user2Marks = reviewRepository.getReviewsByUserId(userId).associate{
            it.contentId to it.mark
        }

        val score = calculateScore(user1Marks, user2Marks)
        return UserTasteMatchScoreResponse(
            score = score.first,
            userId = userId.toString()
        )
    }


    companion object{
        const val DEFAULT_PAGE_SIZE = 10
        const val MAX_MARK = 10
        const val SIMILARITY_THRESHOLD = 0.7
        const val POSITIVE_MARK_THRESHOLD = 5
        const val USER_MIN_MARK_COUNT_THRESHOLD = 10

        fun calculateScore(userMarks1: Map<Long, Int>, userMarks2: Map<Long, Int>): Pair<Float, Int> {
            var sum = 0
            var markCount = 0
            userMarks1.forEach { (contentId, mark) ->
                val mark2 = userMarks2[contentId]
                if(mark2 != null) {
                    markCount++
                    sum += abs(mark - mark2)
                }
            }
            val avg = sum.toFloat() / (MAX_MARK * markCount)
            return (1f - avg) to markCount
        }

        fun getRecommendedElements(content1: Map<Long, Int>, content2: Map<Long, Int>): Set<Long>{
            val recommended = mutableSetOf<Long>()
            content2.forEach { (contentId, mark) ->
                if(mark >= POSITIVE_MARK_THRESHOLD && !content1.containsKey(contentId)){
                    recommended.add(contentId)
                }
            }
            return recommended
        }
    }

    class UserScore(
        val id: UUID,
        val score: Float,
        val markCount: Int
    )

}