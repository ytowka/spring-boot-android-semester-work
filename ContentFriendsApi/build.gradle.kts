plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "com.danilkha"
version = "1.0-SNAPSHOT"


dependencies {
    implementation(libs.kotlinx.serialization)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
