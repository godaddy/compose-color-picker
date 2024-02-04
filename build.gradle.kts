// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
        classpath("com.android.tools.build:gradle:8.2.2")
    }
}

group = "com.godaddy"
version = "0.5.0"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    id("com.diffplug.spotless") version "6.10.0"
    id("org.jetbrains.compose") version "1.5.12" apply false
}

apply("${project.rootDir}/gradle/spotless.gradle")