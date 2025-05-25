import net.infumia.gradle.applyPublish

applyPublish("common")

dependencies { compileOnly(libs.annotations) }
