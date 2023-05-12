/*
 * Copyright 2022 VastGui guihy2019@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.pluginversion.vastgui.Google
import com.pluginversion.vastgui.Jetbrains
import com.pluginversion.vastgui.Squareup
import com.pluginversion.vastgui.Version

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
    id("com.pluginversion.vastgui")
}

java {
    sourceCompatibility = Version.java_version
    targetCompatibility = Version.java_version
}

sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
}

dependencies {
    api(project(":libraries:VastJIntent-Annotation"))
    implementation(Squareup.kotlinpoet)
    implementation(Squareup.kotlinpoet_ksp)
    implementation(Jetbrains.kotlin_stdlib)
    implementation(Google.symbol_processing_api)
}