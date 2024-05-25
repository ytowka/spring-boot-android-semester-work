package com.danilkha.conentfrientdsclient.core.network



import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
class NetworkModule {

    @Single
    fun httpClient(
        authenticator: AuthenticationInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                addInterceptor(authenticator)
                addInterceptor(loggingInterceptor)
            }
            defaultRequest {
                url(baseUrl)
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation){
                json()
            }
        }
    }

    @Single
    @Named("auth")
    fun authHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                config {
                    addInterceptor(loggingInterceptor)
                }
            }
            defaultRequest {
                url(baseUrl)
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation){
                json()
            }
        }
    }

    @Single
    fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    companion object {
        //const val baseUrl = "http://192.168.110.49:8080" // hotspot
        const val baseUrl = "http://192.168.0.128:8080" // home
        //const val baseUrl = "http://10.6.63.137:8080" // surf
    }
}
