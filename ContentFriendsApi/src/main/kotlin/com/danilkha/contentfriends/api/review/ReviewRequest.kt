package com.danilkha.contentfriends.api.review

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewRequest(
    @SerialName("content_id") val contentId: Long,
    @SerialName("mark") val mark: Int,
    val text: String
)
