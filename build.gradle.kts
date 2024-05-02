plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    kotlin("jvm") version "1.9.23" apply false
    kotlin("plugin.serialization")  version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.23-1.0.20" apply false
}