pluginManagement {
    repositories {
        mavenCentral()
        maven(url = "https://maven.fabricmc.net/")
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
}

include("Cubic-Core")
include("Cubic-Fabric")
include("Cubic-Plugin")