package com.danilkha.conentfrientdsclient.features.content.ui

import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto

data class ContentModel(
    val id: Long,
    val themeId: Long,
    val name: String,
    val imageUrl: String?,
    val avgMark: Float?,
    val reviewCount: Int,
)


fun ContentDto.toContentModel() = ContentModel(
    id = id,
    themeId = themeId,
    name = name,
    imageUrl = imageUrl,
    avgMark = avgMark,
    reviewCount = reviewCount,
)