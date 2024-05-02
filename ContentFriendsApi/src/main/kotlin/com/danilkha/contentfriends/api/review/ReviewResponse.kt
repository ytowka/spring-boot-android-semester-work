package com.danilkha.contentfriends.api.review

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ReviewResponse (
    val userId: String,
    val contentId: Long,
    val userAvatarUrl: String?,
    val userName: String,
    val mark: Int,
    val text: String
)