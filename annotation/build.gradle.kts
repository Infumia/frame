import net.infumia.gradle.applyPublish

applyPublish("annotation")

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":config"))
    compileOnly(project(":injector"))
    compileOnly(project(":api"))
    compileOnly(project(":annotations"))

    compileOnly(libs.minecraft.one.eight.eight.paper)
    compileOnly(libs.geantyref)
    compileOnly(libs.adventure.api)
    compileOnly(libs.annotations)
}
