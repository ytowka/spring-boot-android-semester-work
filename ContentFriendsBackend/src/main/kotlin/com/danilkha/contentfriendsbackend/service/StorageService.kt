package com.danilkha.contentfriendsbackend.service

import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.net.URL

interface StorageService {

    fun saveFile(file: MultipartFile): String

    fun saveFile(fileName: String, inputStream: InputStream, size: Long): String

    fun saveImageFrom(url: URL, filename: String): String

    fun getFile(filename: String): InputStream
}