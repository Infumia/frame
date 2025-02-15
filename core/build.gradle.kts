import net.infumia.gradle.applyPublish

applyPublish("core")

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":injector"))
    compileOnly(project(":service"))
    compileOnly(project(":api"))
    compileOnly(libs.minecraft.one.eight.eight.paper)

    compileOnly(libs.guice) { isTransitive = false }
    compileOnly(libs.geantyref)
    compileOnly(libs.adventure.api)
}
