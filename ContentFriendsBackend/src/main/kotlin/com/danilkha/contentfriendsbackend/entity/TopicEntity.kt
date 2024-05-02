package com.danilkha.contentfriendsbackend.entity

import com.danilkha.contentfriends.api.theme.ThemeResponse
import com.danilkha.contentfriendsbackend.controller.FILES_PATH
import javax.persistence.*


@Entity
@Table(name = "topic")
data class TopicEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val contentCount: Int,
    val imageUrl: String,
)

fun TopicEntity.toResponse(): ThemeResponse = ThemeResponse(
    id = id,
    name = name,
    contentCount = contentCount,
    imageUrl = "$FILES_PATH/$imageUrl"
)