import net.infumia.gradle.applyPublish

applyPublish("annotations")

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":config"))
    compileOnly(project(":injector"))
    compileOnly(project(":api"))

    compileOnly(libs.minecraft.one.eight.eight.paper)
    compileOnly(libs.geantyref)
    compileOnly(libs.adventure.api)
    compileOnly(libs.annotations)
}
