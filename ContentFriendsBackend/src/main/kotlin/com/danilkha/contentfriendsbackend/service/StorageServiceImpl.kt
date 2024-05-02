package com.danilkha.contentfriendsbackend.service

import io.minio.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.util.*

@Service
class StorageServiceImpl(
    private val minioClient: MinioClient
) : StorageService{

    override fun saveFile(file: MultipartFile): String {
        try {
            val bucketExists = minioClient.bucketExists(
                BucketExistsArgs
                    .builder()
                    .bucket(FILES_BUCKET)
                    .build()
            )
            if (!bucketExists){
                minioClient.makeBucket(
                    MakeBucketArgs.builder()
                        .bucket(FILES_BUCKET)
                        .build()
                )
            }
            val objectId = UUID.randomUUID().toString()+file.originalFilename
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(FILES_BUCKET)
                    .`object`(objectId)
                    .stream(file.inputStream, file.size, -1)
                    .build()
            )
            return objectId
        }catch (e: Exception) {
            e.printStackTrace()
           throw e
        }
    }

    override fun getFile(filename: String): InputStream {
        return minioClient.getObject(GetObjectArgs
            .builder()
            .bucket(FILES_BUCKET)
            .`object`(filename)
            .build()
        )
    }

    companion object{
        private const val FILES_BUCKET = "files"
    }
}