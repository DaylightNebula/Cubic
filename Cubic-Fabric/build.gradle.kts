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
    implementation(project(":Cubic-Core"))

    minecraft("com.mojang:minecraft:1.20.4")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:0.15.6")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.10.17+kotlin.1.9.22")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.95.1+1.20.4")
}
