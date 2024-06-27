plugins {
    id("java")
}

group = "com.maximde"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
}