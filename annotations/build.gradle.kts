import net.infumia.gradle.applyPublish

applyPublish("annotations")

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":config"))
    compileOnly(project(":api"))
    compileOnly(project(":injector"))
    compileOnly(libs.minecraft.one.eight.eight.paper)

    compileOnly(libs.guice) { isTransitive = false }
    compileOnly(libs.geantyref)
    compileOnly(libs.adventure.api)
}
