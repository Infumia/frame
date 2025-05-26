import net.infumia.gradle.applySpotless

plugins { java }

subprojects {
    apply<JavaPlugin>()
    apply<JavaLibraryPlugin>()

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        testImplementation(platform(rootProject.libs.junit))
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    tasks.withType<Test> { useJUnitPlatform() }
}

applySpotless()
