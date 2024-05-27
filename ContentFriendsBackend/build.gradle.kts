import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    kotlin("plugin.serialization")
}

group = "com.danilkha"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.spring.boot.starter.data)
    implementation(libs.spring.boot.starter.jdbc)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.test)
    implementation(libs.spring.boot.security.test)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.logging)
    implementation (libs.hibernate.core)
    implementation(libs.minio)
    implementation(libs.apache.commons.io)
    implementation(libs.jwt.parser)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlin.reflect)
    api(project(":ContentFriendsApi"))
    implementation(libs.springfox.swagger2){
        exclude("io.swagger", "swagger-annotations")
        exclude("io.swagger", "swagger-models")
    }
    implementation(libs.springfox.swagger2.models)
    implementation(libs.springfox.swagger2.annotations)
    implementation(libs.springfox.swagger2.ui)
    runtimeOnly(libs.postges)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.security.test)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
