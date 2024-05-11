package com.danilkha.conentfrientdsclient.features.content.domain.dto

data class ContentDto(
    val id: Long,
    val themeId: Long,
    val name: String,
    val imageUrl: String?,
    val avgMark: Float?,
    val reviewCount: Int,
)