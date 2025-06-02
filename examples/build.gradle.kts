plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta13"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "net.infumia"

version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://central.sonatype.com/repository/maven-snapshots/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    implementation("net.infumia:frame:0.2.12-SNAPSHOT")
    implementation("io.leangen.geantyref:geantyref:1.3.16")
}

tasks {
    runServer {
        minecraftVersion("1.21.4")
        jvmArgs("-Dcom.mojang.eula.agree=true")
        downloadPlugins { github("jpenilla", "TabTPS", "v1.3.27", "tabtps-spigot-1.3.27.jar") }
    }
}
