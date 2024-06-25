plugins {
    id("java")
}

group = "com.maximde"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven ("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly ("org.projectlombok:lombok:1.18.32")
    annotationProcessor ("org.projectlombok:lombok:1.18.32")
    compileOnly ("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
}

