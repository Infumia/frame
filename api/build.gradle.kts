import net.infumia.gradle.applyPublish

applyPublish()

dependencies {
    compileOnlyApi(project(":common"))
    compileOnlyApi(project(":injector"))
    compileOnlyApi(project(":service"))

    runtimeOnly(project(":core"))

    compileOnly(libs.minecraft.one.eight.eight.paper)
    compileOnly(libs.geantyref)
    compileOnly(libs.adventure.api)
    compileOnly(libs.annotations)
}
