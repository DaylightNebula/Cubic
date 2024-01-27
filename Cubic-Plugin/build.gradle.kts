plugins {
    kotlin("jvm") version "1.9.22"
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    implementation(project(":Cubic-Core"))

    // serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.6.0")
}
