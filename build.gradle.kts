// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.javiersc.gradle.plugins.nexus") version "0.1.0-rc.8"
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.android.tools.build:gradle:7.0.3")
    }
}

allprojects {
    group = properties["projects.group"].toString()
    version = properties["projects.version"].toString()
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
