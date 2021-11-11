import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.plugin.statistics.ReportStatisticsToElasticSearch.password

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka") version "1.5.30"
    id("org.jetbrains.compose") version "1.0.0-beta5"
    id("com.android.library")
    id("kotlin-android-extensions")
    id("maven-publish")
    id("signing")
}

kotlin {
    android("android") {
        publishLibraryVariants("release")
    }
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                implementation("com.github.ajalt.colormath:colormath:3.1.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.3.1")
                api("androidx.core:core-ktx:1.7.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val jvmMain by getting {
            dependencies {
                api(compose.preview)
            }
        }
        val jvmTest by getting
    }
}

android {
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(31)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks {
    create<Jar>("javadocJar") {
        dependsOn(dokkaJavadoc)
        archiveClassifier.set("javadoc")
        from(dokkaJavadoc.get().outputDirectory)
    }
}
val sonatypeUsername: String? = System.getenv("ORG_GRADLE_PROJECT_mavenCentralUsername")
val sonatypePassword: String? = System.getenv("ORG_GRADLE_PROJECT_mavenCentralPassword")


afterEvaluate {
    configure<PublishingExtension> {
        publications.all {
            val mavenPublication = this as? MavenPublication

            mavenPublication?.artifactId =
                "compose-color-picker${"-$name".takeUnless { "kotlinMultiplatform" in name }.orEmpty()}".removeSuffix("Release")
        }
    }
}
val keyId = (System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKeyId")
    ?: project.properties["signingInMemoryKeyId"]) as String
val keyPassword = (System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKey")
    ?: project.properties["signingInMemoryKeyPassword"]) as String
val key = (System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKey")
    ?: project.properties["signingInMemoryKey"]) as String

signing {
     useInMemoryPgpKeys(
         keyId,
         key,
         keyPassword,
     )

    sign(configurations.archives.get())
    sign(publishing.publications)
}
publishing {

    publications.withType(MavenPublication::class) {
        groupId = "com.godaddy.android.colorpicker"
        artifactId = "compose-color-picker"
        version = "0.2.1"

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

