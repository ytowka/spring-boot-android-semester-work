package com.danilkha.contentfriendsbackend.entity

interface ContentWithReviewView{
    val id: Long
    val themeId: Long
    val name: String
    val image: String?
    val count: Int?
    val avg: Float?
}