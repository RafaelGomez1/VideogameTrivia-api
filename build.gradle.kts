import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.9.0"
    id("com.diffplug.spotless") version "6.18.0"
    kotlin("plugin.spring") version "1.9.0"
    id("java-test-fixtures")
    `jvm-test-suite`
    jacoco
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
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC")

    // Spotless
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.9.0")

    // Functional Programming
    implementation("io.arrow-kt:arrow-core:1.2.0")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.1.2")

    // Chat GPT
    implementation("io.github.flashvayne:chatgpt-spring-boot-starter:1.0.4")

    // Password Encryption
    implementation("org.mindrot:jbcrypt:0.4")

    // Event Bus
    implementation("org.axonframework:axon-messaging:4.9.0")
    implementation("io.github.serpro69:kotlin-faker:1.14.0")

    // Testing
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.github.serpro69:kotlin-faker")

    testFixturesImplementation("io.github.serpro69:kotlin-faker:1.15.0")

    // Coroutines Testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
        jvmTarget = "17"
    }
}

testing {
    suites {
        register("konsistTest", JvmTestSuite::class) {
            dependencies {
                implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
                implementation("com.lemonappdev:konsist:0.13.0")
                implementation("org.springframework.boot:spring-boot-starter-web")
            }
        }
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

tasks.named("check") {
    dependsOn(testing.suites.named("konsistTest"))
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    dependsOn(tasks.test)
}
