plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0" }

rootProject.name = "frame"

include("common", "config", "service", "injector")

include("api", "core", "annotations")
