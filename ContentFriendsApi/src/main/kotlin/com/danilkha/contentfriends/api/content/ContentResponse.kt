package com.danilkha.contentfriends.api.content

import kotlinx.serialization.Serializable
import java.awt.Image

@Serializable
data class ContentResponse(
    val id: Long,
    val name: String,
    val imageUrl: String?,
    val avgMark: Float,
    val reviewCount: Int,
)