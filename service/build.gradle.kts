import net.infumia.gradle.applyPublish

applyPublish("service")

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.geantyref)

    testImplementation(project(":common"))
    testImplementation(libs.geantyref)
}
