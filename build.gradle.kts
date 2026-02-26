/*
 * Copyright 2026 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.kotlinGradlePlugin)
        classpath(libs.androidGradlePlugin)
        classpath(libs.mokoGradlePlugin)
        classpath(libs.mobileMultiplatformGradlePlugin)
        classpath(":build-logic")
    }
}

val mokoVersion = libs.versions.mokoStateVersion.get()

allprojects {
    plugins.withId("org.gradle.maven-publish") {
        group = "dev.icerock.moko"
        version = mokoVersion
    }
}

// required for nexus plugin
group = "dev.icerock.moko"
version = mokoVersion

apply(plugin = "nexus-publication-convention")

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}
