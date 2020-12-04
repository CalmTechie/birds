import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by extra
val ktor_version: String by extra
val koin_version: String by extra
val mariaDbDriver_version: String by extra
val logback_version: String by extra
val exposed_version: String by extra

plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"
    application
}
group = "ru.korolev"
version = "0.0.1"

repositories {
    mavenCentral()
    jcenter()
    maven {
        url = uri("https://dl.bintray.com/kotlin/ktor")
    }
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlinx")
    }
}
dependencies {
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-html-builder:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")

    implementation("org.koin:koin-ktor:$koin_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.mariadb.jdbc:mariadb-java-client:$mariaDbDriver_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "ServerKt"
}