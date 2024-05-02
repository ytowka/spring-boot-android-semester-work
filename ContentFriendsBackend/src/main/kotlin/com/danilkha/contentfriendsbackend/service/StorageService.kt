package com.danilkha.contentfriendsbackend.service

import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

interface StorageService {

    fun saveFile(file: MultipartFile): String

    fun getFile(filename: String): InputStream
}