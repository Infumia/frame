import net.infumia.gradle.applySpotless

plugins { java }

subprojects {
    apply<JavaPlugin>()
    apply<JavaLibraryPlugin>()

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

applySpotless()
