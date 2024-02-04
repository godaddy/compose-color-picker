plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka") version "1.9.10"
    id("org.jetbrains.compose")
    id("com.android.library")
    id("maven-publish")
    id("signing")
}

kotlin {
    androidTarget("android") {
        publishLibraryVariants("release")
    }
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation("com.github.ajalt.colormath:colormath:3.4.0")
            }
        }
    }

    jvmToolchain(17)
}

android {
    namespace = "com.godaddy.common.colorpicker"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}

val dokkaOutputDir = layout.buildDirectory.dir("dokka")

tasks.dokkaHtml.configure {
    outputDirectory.set(dokkaOutputDir)
}

val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") {
    delete(dokkaOutputDir)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
}

val sonatypeUsername: String? = System.getenv("ORG_GRADLE_PROJECT_mavenCentralUsername")
val sonatypePassword: String? = System.getenv("ORG_GRADLE_PROJECT_mavenCentralPassword")

if (sonatypeUsername != null && sonatypePassword != null) {

    afterEvaluate {
        configure<PublishingExtension> {
            publications.all {
                val mavenPublication = this as? MavenPublication

                mavenPublication?.artifactId =
                    "compose-color-picker${
                        "-$name".takeUnless { "kotlinMultiplatform" in name }.orEmpty()
                    }".removeSuffix("Release")
            }
        }
    }

    signing {
        setRequired {
            // signing is only required if the artifacts are to be published
            gradle.taskGraph.allTasks.any { PublishToMavenRepository::class == it.javaClass }
        }
        sign(configurations.archives.get())
        sign(publishing.publications)
    }
    publishing {

        publications.withType(MavenPublication::class) {
            groupId = "com.godaddy.android.colorpicker"
            artifactId = "compose-color-picker"
            version = "0.7.0"

            artifact(tasks["javadocJar"])

            pom {

                name.set("compose-color-picker")
                description.set("A compose component for picking a color")
                url.set("https://github.com/godaddy/compose-color-picker")

                licenses {
                    license {
                        name.set("The MIT License (MIT)")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("godaddy")
                        name.set("GoDaddy")
                    }
                }
                organization {
                    name.set("GoDaddy")
                }
                scm {
                    connection.set("scm:git:git://github.com/godaddy/compose-color-picker.git")
                    developerConnection.set("scm:git:ssh://git@github.com/godaddy/compose-color-picker.git")
                    url.set("https://github.com/godaddy/compose-color-picker")
                }
            }
        }

        repositories {
            maven {
                setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = sonatypeUsername
                    password = sonatypePassword
                }
            }
        }
    }
}
