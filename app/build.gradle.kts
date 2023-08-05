
plugins {
    id("org.jetbrains.compose") version "1.3.0"
    id("com.android.application")
    kotlin("android")
}

group = "com.godaddy"
version = "1.0"


dependencies {
    implementation(project(":color-picker"))
    implementation ("androidx.activity:activity-compose:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.navigation:navigation-runtime-ktx:2.5.3")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation(compose.material)
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.godaddy.android.colorpicker"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}