import net.infumia.gradle.applySpotless

plugins { java }

subprojects { apply<JavaPlugin>() }

applySpotless()
