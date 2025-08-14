plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "net.infumia"

version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
        vendor = JvmVendorSpec.ADOPTIUM
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")

    implementation("net.infumia:frame:1.0.0-SNAPSHOT")
    implementation("net.infumia:title-updater:0.1.3")
    implementation("io.leangen.geantyref:geantyref:2.0.1")
}

tasks {
    runServer {
        minecraftVersion("1.21.8")
        jvmArgs("-Dcom.mojang.eula.agree=true")
        downloadPlugins { github("jpenilla", "TabTPS", "v1.3.28", "tabtps-spigot-1.3.28.jar") }
    }
}
