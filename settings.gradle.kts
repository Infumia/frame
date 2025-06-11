plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

rootProject.name = "frame"

include(
    "common",
    "injector",
    "service",
    "api",
    "core",
)

// include("config", "annotations", "annotation")
