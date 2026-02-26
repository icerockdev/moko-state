/*
 * Copyright 2026 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import java.util.Base64

plugins {
    id("org.gradle.maven-publish")
}

publishing {
    publications.withType<MavenPublication> {
        // Provide artifacts information requited by Maven Central
        pom {
            name.set("MOKO state")
            description.set("Resources access for Kotlin Multiplatform development (mobile first)")
            url.set("https://github.com/icerockdev/moko-state")
            licenses {
                license {
                    name.set("Apache-2.0")
                    distribution.set("repo")
                    url.set("https://github.com/icerockdev/moko-state/blob/master/LICENSE.md")
                }
            }

            developers {
                // TODO()
            }

            scm {
                connection.set("scm:git:ssh://github.com/icerockdev/moko-state.git")
                developerConnection.set("scm:git:ssh://github.com/icerockdev/moko-state.git")
                url.set("https://github.com/icerockdev/moko-state")
            }
        }
    }
}

val signingKeyId: String? = System.getenv("SIGNING_KEY_ID")
if (signingKeyId != null) {
    apply(plugin = "signing")

    configure<SigningExtension> {
        val signingPassword: String? = System.getenv("SIGNING_PASSWORD")
        val signingKey: String? = System.getenv("SIGNING_KEY")?.let { base64Key ->
            String(Base64.getDecoder().decode(base64Key))
        }

        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
        sign(publishing.publications)
    }

    val signingTasks = tasks.withType<Sign>()
    tasks.withType<AbstractPublishToMaven>().configureEach {
        dependsOn(signingTasks)
    }
}
