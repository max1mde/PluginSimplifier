plugins {
    kotlin("jvm") version "1.9.20"
    `maven-publish`
}

group = "com.maximde"
version = "1.0.7"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.20")
    compileOnly("org.jetbrains:annotations:24.0.1")
}

tasks.jar {
    archiveBaseName.set("PluginSimplifier")
    archiveVersion.set(version.toString())

    // Exclude kotlin and org.jetbrains packages
    exclude("kotlin/**")
    exclude("org/intellij/**")
    exclude("org/jetbrains/**")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}