package com.danilkha.contentfriendsbackend.config

import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinioConfig(
    @Value("\${minio.url}") private val minioUrl: String,
    @Value("\${minio.username}") private val minioUserName: String,
    @Value("\${minio.password}") private val minioPassword: String,
) {


    @Bean
    fun minioClient(): MinioClient {
            return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUserName, minioPassword)
                .build()
    }
}