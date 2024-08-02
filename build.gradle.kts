plugins {
    kotlin("jvm") version "1.9.20"
    `maven-publish`
}

group = "com.maximde"
version = "1.0.5"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

tasks.jar {
    archiveBaseName.set("PluginSimplifier")
    archiveVersion.set(version.toString())
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}