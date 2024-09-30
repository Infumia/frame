import net.infumia.gradle.applySpotless

plugins { java }

subprojects {
    apply<JavaPlugin>()

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

applySpotless()
