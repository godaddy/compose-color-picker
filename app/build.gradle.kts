
plugins {
    id("org.jetbrains.compose") version "1.5.12"
    id("com.android.application")
    kotlin("android")
}

group = "com.godaddy"
version = "1.0"

dependencies {
    implementation(project(":color-picker"))
    implementation ("androidx.activity:activity-compose:1.8.2")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.6")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation(compose.material)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.godaddy.android.colorpicker"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.godaddy.android.colorpicker"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}