import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    id("convention.publication")
    id("org.jetbrains.dokka")
    alias(libs.plugins.kotlinJvm)
}

group = "io.github.sakurajimamaii"
version = libs.versions.log.get()

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

sourceSets["main"].java.srcDir("src/main/kotlin")

kotlin.sourceSets.all {
    languageSettings.optIn("com.log.vastgui.core.annotation.LogApi")
}

dependencies {
    api(libs.vastcore)
    implementation(libs.fastjson2)
    implementation(libs.gson)
    implementation(libs.jackson.databind)
}

extra["PUBLISH_ARTIFACT_ID"] = "log-core"
extra["PUBLISH_DESCRIPTION"] = "Core for log"
extra["PUBLISH_URL"] =
    "https://github.com/SakurajimaMaii/Android-Vast-Extension/tree/develop/libraries/log/core"

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "io.github.sakurajimamaii"
            artifactId = "log-core"
            version = libs.versions.log.get()

            afterEvaluate {
                from(components["java"])
            }
        }
    }
}

tasks.withType<DokkaTaskPartial> {
    moduleName.set("log-core")
}