import net.infumia.gradle.applyPublish

applyPublish("config")

dependencies {
    compileOnly(project(":common"))
    compileOnly(libs.minecraft.one.eight.eight.paper)
}
