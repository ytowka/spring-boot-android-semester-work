package com.danilkha.contentfriendsbackend.controller

import com.danilkha.contentfriendsbackend.service.StorageService
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val FILES_PATH = "/files"

@RestController
@RequestMapping(FILES_PATH)
class FileController(
    private val storageService: StorageService
) {


    @GetMapping("/{filename}")
    fun getFile(@PathVariable filename: String): ResponseEntity<Resource> {
        val isJpg = filename.endsWith(".jpg")
        val contentType = if(isJpg){
            MediaType.IMAGE_JPEG
        }else MediaType.IMAGE_PNG
        return ResponseEntity.ok()
            .contentType(contentType)
            .body(ByteArrayResource(storageService.getFile(filename).readBytes()))
    }
}