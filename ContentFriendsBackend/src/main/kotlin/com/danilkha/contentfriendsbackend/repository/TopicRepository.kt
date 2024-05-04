package com.danilkha.contentfriendsbackend.repository

import com.danilkha.contentfriendsbackend.entity.TopicEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


interface TopicRepository : JpaRepository<TopicEntity, Long>