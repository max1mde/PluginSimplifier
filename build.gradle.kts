plugins {
    kotlin("jvm") version "1.9.20"
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.maximde"
version = "1.0.4"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveBaseName.set("PluginSimplifier")
    archiveVersion.set(version.toString())
    dependencies {
        exclude(dependency("org.spigotmc:spigot-api"))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}