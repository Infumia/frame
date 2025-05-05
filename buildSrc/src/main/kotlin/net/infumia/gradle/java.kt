package net.infumia.gradle

import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.gradle.kotlin.dsl.*

fun Project.applyJava(javaVersion: Int = 8) {
    apply<JavaPlugin>()
    apply<JavaLibraryPlugin>()

    repositories.mavenCentral()

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(javaVersion)
            vendor = JvmVendorSpec.ADOPTIUM
        }
    }
}
