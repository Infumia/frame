import net.infumia.gradle.applyPublish

applyPublish()

dependencies {
    compileOnlyApi(project(":common"))
    compileOnlyApi(project(":injector"))
    compileOnlyApi(project(":service"))

    runtimeOnly(project(":core"))
    runtimeOnly(project(":common"))
    runtimeOnly(project(":injector"))
    runtimeOnly(project(":service"))

    compileOnly(libs.minecraft.one.eight.eight.paper)
    compileOnly(libs.geantyref)
    compileOnly(libs.adventure.api)
    compileOnly(libs.annotations)
}
