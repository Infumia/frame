plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0" }

rootProject.name = "frame"

include("common", "config", "injector", "service")

include("api", "annotations")

include("core")
