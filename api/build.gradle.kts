import net.infumia.gradle.applyPublish

applyPublish()

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":injector"))
    compileOnly(project(":service"))

    runtimeOnly(project(":core"))

    compileOnly(libs.minecraft.one.eight.eight.paper)
    compileOnly(libs.geantyref)
    compileOnly(libs.adventure.api)
    compileOnly(libs.annotations)
}
