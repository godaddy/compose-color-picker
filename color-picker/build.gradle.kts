plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka") version "1.7.20"
    id("org.jetbrains.compose") version "1.2.2"
    id("com.android.library")
    id("maven-publish")
    id("signing")
}

kotlin {
    android("android") {
        publishLibraryVariants("release")
    }
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                implementation(compose.material)
                implementation("com.github.ajalt.colormath:colormath:3.2.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.preview)
            }
        }
        val jvmTest by getting
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

android {
    namespace = "com.godaddy.common.colorpicker"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

val dokkaOutputDir = buildDir.resolve("dokka")

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
        version = "0.6.0"

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

