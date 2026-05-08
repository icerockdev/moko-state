/*
 * Copyright 2026 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("dev.icerock.moko.gradle.multiplatform.all")
    id("dev.icerock.moko.gradle.detekt")
}

android {
    namespace = "dev.icerock.moko.remotestate"
}

dependencies {
    commonMainImplementation(libs.coroutines)
    commonTestImplementation(kotlin("test"))
}
