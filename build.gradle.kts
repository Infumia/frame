import net.infumia.gradle.applySpotless

plugins {
    java
    `java-library`
}

subprojects {
    apply<JavaPlugin>()
    apply<JavaLibraryPlugin>()

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

applySpotless()
