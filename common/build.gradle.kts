import net.infumia.gradle.applyPublish

applyPublish("common")

dependencies {
    compileOnly(libs.geantyref)
    compileOnly(libs.annotations)

    testImplementation(libs.geantyref)
}
