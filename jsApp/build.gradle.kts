plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.2.2"
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting  {
            dependencies {
                implementation(project(":color-picker"))
            }
        }
    }
}

compose.experimental {
    web.application {}
}
