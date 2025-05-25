plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

rootProject.name = "frame"

include("common", "config", "injector", "service")

include("api")

include("core")
