import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.9.0"
    id("com.diffplug.spotless") version "6.18.0"
    kotlin("plugin.spring") version "1.9.0"
    id("java-test-fixtures")
}

group = "rafa.gomez"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
//    implementation("org.springframework.boot:spring-boot-starter-security")
//    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Spotless
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.9.0")

    // Functional Programming
    implementation("io.arrow-kt:arrow-core:1.2.0")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.1.2")

    // Chat GPT
    implementation("io.github.flashvayne:chatgpt-spring-boot-starter:1.0.4")

    // Password Encryption
    implementation("org.mindrot:jbcrypt:0.4")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")

    // Coroutines Testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
        jvmTarget = "17"
    }
}

spotless {
    kotlin {
        ktlint().setEditorConfigPath("$rootDir/.editorconfig")
    }
    kotlinGradle {
        ktlint().setEditorConfigPath("$rootDir/.editorconfig")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
