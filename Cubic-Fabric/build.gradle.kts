plugins {
    kotlin("jvm") version "1.9.22"
    id("fabric-loom") version "1.5.6"
}

repositories {
    mavenCentral()
    maven(url = "https://maven.fabricmc.net/")
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    minecraft("com.mojang:minecraft:1.20.4")
    mappings("net.fabricmc:yarn:1.20.4+build.3:v2")
    modImplementation("net.fabricmc:fabric-loader:0.15.6")

    modImplementation("net.fabricmc:fabric-language-kotlin:1.10.17+kotlin.1.9.22")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.95.1+1.20.4")
}

kotlin {
    jvmToolchain(17)
}
