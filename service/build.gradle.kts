import net.infumia.gradle.applyPublish

applyPublish("service")

dependencies {
    compileOnly(project(":common"))
    compileOnly(libs.geantyref)
    compileOnly(libs.annotations)
}
