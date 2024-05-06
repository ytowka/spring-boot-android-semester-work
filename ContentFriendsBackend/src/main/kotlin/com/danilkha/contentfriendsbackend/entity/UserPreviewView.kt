package com.danilkha.contentfriendsbackend.entity

import com.danilkha.contentfriendsbackend.model.Role
import java.util.*

interface UserPreviewView {
    val id: UUID
    val login: String
    val avatarFileName: String?
}