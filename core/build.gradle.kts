import net.infumia.gradle.applyPublish

applyPublish()

dependencies {
    compileOnly(project(":common"))
    compileOnly(libs.minecraft.one.eight.eight.paper)

    compileOnly(libs.guice) { isTransitive = false }
    compileOnly(libs.geantyref)
    compileOnly(libs.adventure.api)
}
