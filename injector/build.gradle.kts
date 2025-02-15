import net.infumia.gradle.applyPublish

applyPublish("injector")

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":service"))

    compileOnly(libs.guice) { isTransitive = false }
    compileOnly(libs.geantyref)
}
