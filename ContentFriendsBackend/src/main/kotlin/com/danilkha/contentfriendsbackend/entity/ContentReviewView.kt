package com.danilkha.contentfriendsbackend.entity

interface ContentReviewView{

    val contentEntity: ContentEntity
    val count: Int
    val avg: Float
}