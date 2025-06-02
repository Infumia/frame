package net.infumia.gradle

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import com.diffplug.spotless.LineEnding
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

fun Project.applySpotless() {
    val subProjects = subprojects.map { it.projectDir.toRelativeString(projectDir) }

    repositories.mavenCentral()

    apply<SpotlessPlugin>()

    extensions.configure<SpotlessExtension> {
        isEnforceCheck = false
        lineEndings = LineEnding.UNIX

        val prettierConfig =
            mapOf(
                "prettier" to "3.5.3",
                "prettier-plugin-java" to "2.6.7",
                "prettier-plugin-toml" to "2.0.4",
            )

        yaml {
            target(".github/**/*.yml")
            endWithNewline()
            trimTrailingWhitespace()
            jackson().yamlFeature("LITERAL_BLOCK_STYLE", true).yamlFeature("SPLIT_LINES", false)
        }

        json {
            target("renovate.json")
            endWithNewline()
            trimTrailingWhitespace()
            jackson()
        }

        format("toml") {
            target("gradle/libs.versions.toml", "examples/gradle/libs.versions.toml")
            endWithNewline()
            trimTrailingWhitespace()
            prettier(prettierConfig)
                .config(mapOf("parser" to "toml", "plugins" to listOf("prettier-plugin-toml")))
        }

        kotlin {
            target(
                "buildSrc/src/**/kotlin/**/*.kt",
                "buildSrc/**/*.gradle.kts",
                "examples/src/**/kotlin/**/*.kt",
                "examples/**/*.gradle.kts",
                "*.gradle.kts",
                *subProjects.map { "$it/*.gradle.kts" }.toTypedArray(),
                *subProjects.map { "$it/src/**/kotlin/**/*.kt" }.toTypedArray(),
            )
            endWithNewline()
            trimTrailingWhitespace()
            ktfmt().kotlinlangStyle().configure {
                it.setMaxWidth(100)
                it.setBlockIndent(4)
                it.setContinuationIndent(4)
                it.setRemoveUnusedImports(true)
            }
        }

        java {
            target(
                *subProjects.map { "$it/src/**/java/**/*.java" }.toTypedArray(),
                "examples/src/**/java/**/*.java",
            )
            importOrder()
            removeUnusedImports()
            endWithNewline()
            trimTrailingWhitespace()
            prettier(prettierConfig)
                .config(
                    mapOf(
                        "parser" to "java",
                        "tabWidth" to 4,
                        "useTabs" to false,
                        "printWidth" to 100,
                        "plugins" to listOf("prettier-plugin-java"),
                    )
                )
        }
    }
}
