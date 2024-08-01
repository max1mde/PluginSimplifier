plugins {
    kotlin("jvm") version "1.9.20"
    `maven-publish`
}

group = "com.maximde"
version = "1.0.3"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
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