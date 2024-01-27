pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven(url = "https://maven.fabricmc.net/")
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
}

include(":Cubic-Core")
include(":Cubic-Fabric")
include(":Cubic-Plugin")