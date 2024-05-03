package com.danilkha.contentfriendsbackend.entity

import javax.persistence.*

@Entity
@Table(name = "review")
data class ReviewEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val userId: Long,
    val contentId: Long,
    val text: String,
    val mark: Int
)